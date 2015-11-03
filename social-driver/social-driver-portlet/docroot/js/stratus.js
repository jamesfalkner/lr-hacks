/**
 * 	Stratus - JavaScript Cumulus Plugin (codenamed as JStratus)
 * 	Based on WP-Cumulus plugin by Roy Tanck (http://www.roytanck.com)
 *
 * 	@author Dawid Fatyga (fatyga@student.agh.edu.pl)
 * 	@date 14.02.2009 02:38:38
 * 	@version 0.1.2
 *
 *  Copyright 2008, Dawid Fatyga
 *
 * 	This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * 	NOTE! (It will be fixed later): 
 * 	To run the Stratus on Chrome there should be at least one <script> tag in the head, because
 * 	do not allow to modify the Html DOM Tree in the head of document after loading the page.
 *
 * 	To run the Stratus on IE, the styles have to be defined in the html explicite. You can simply
 *	copy and paste this template to Your page (the colors are default):
 
.tagClass {
	position: absolute;
	padding: 3px;
	color: #fff;
} 
.tagClass:hover {
	text-decoration: none;
	color: #ffdb00;
	border: solid 1px #ffdb00;
}
					
 * 	IDEA: Mouse panning, zooming?
 * 	TODO: Loading tags from file / script (JSON / XML)-> Script which can generate Tags.
 * 	TODO: Documentation.
 * 	TODO: Z-sorting.
 * 	FIXME: Centering the Tags.
 * 	FIXME: Calculating the color instead using the Opacity property (especialy for Chrome).
 * 	TODO: Customizable nodes inside the link. eg. Images.
 * 	FIXME: Customizable styles for Tags disables the default hilight Color.
 * 	FIXME: Mouseleave event does not work properly.
 * 	TODO: Customizable styles also when creating the tag.
 *
 * 	IDEA: Inheritance form customizable styles ??
 * 		var MyTag = new Class({
 * 			implements: Tag,
 * 			setStyle: function(){
 *				...
 * 			}
 * 		});
 */

/** Configurable Constants */
var Config = {
	Smallest: 8,		/* Font size for smalest tag in pt's */
	Biggest: 18,		/* Font size for biggest tag in pt's, it is recomended not to change this value. */
	Depth: 150,			/* Perspective depth */
	Speed: 100,			/* Speed of Rotation */
	Opacity: true, 		/* Use opacity instead of colors */
	Background: "#fff",	/* If Opacity == false, Background is used with Color to make gradient */
	Color: "#fff",		/* Default Color for tags */
	Hover: "#ffdb00",	/* Color when tags are hovered */
	Radius: 75,			/* Default cloud radius */
	UseFx: false,		/* If UseFx == true, animation is performed with Fx.Morph (owful performace :/)*/
	AnimationTime: 25	/* Fx.Morph animation time and interval, the less it is the faster the animation is */
};

var SizeRange = Config.Biggest - Config.Smallest;
var Radian = Math.PI / 180;

/** Postponds the function to be invoked after DOM initialization */
function invokeLater(fn, bind){
	window.addEvent("domready", fn.pass([], bind));
}


/** Generating the Sine and Cosine tables will speed up
 * 	the calculations. I hope. */
var s = [], c = [], i = 0;
while(i < 3600){
	s[i] = Math.sin(i/10 * Radian);
	c[i] = Math.cos(i/10 * Radian);
	i+=1;
}

var Sine = $A(s);
var Cosine = $A(c);

function sine(angle){
	while(angle < 0) angle += 360;
	return Sine[Math.round(angle*10) % 3600];
}
function cosine(angle){
	return Cosine[Math.round(Math.abs(angle)*10) % 3600];
}

/** Place where we add Tags */
var Body;	
invokeLater(function() {
	Body = $(document.body);
});

/** Vector class */
var Vector = new Class({
	/** Also provides copy constructor */
	initialize: function(x, y, z){
		if(typeof x == "object") this.set(x.x, x.y, x.z);
		else this.set(x, y, z);
	},

	mult: function(factor){
		this.x *= factor;
		this.y *= factor;
		this.z *= factor;
		return this;
	},

	/** Returns square of length */
	sqr: function(){
		return this.x* this.x + this.y*this.y + this.z* this.z;
	},

	set: function(x, y, z){
		this.x = $pick(x, 0);
		this.y = $pick(y, 0);
		this.z = $pick(z, 0);
	},

	longEnough: function(){
		return Math.abs(this.x) > 0.01 || Math.abs(this.y) > 0.01 || Math.abs(this.z) > 0.01;
	},

	normalize: function(length){
		var l = Math.sqrt(this.sqr());
		return this.mult($pick(length, 1)/l);
	}
});

var ZeroVector = new Vector();

/** Mouse Atractor Class */
var Atractor = new Class({
	Implements: Options,
	options : {
		id: null,
		position: "",	/* May be 'absolute' */
		width: 200,		
		height: 200,
		top: 0,			
		left: 0
	},
	
	/** If element with proper id is found, then it is used,
	 *	otherwise, defaulut one is created. */
	initialize: function(options){
		this.setOptions(options || {});
		this.mouse = new Vector();

		this.element = $(this.options.id);
		var styles = {
			'position': this.options.position,
			'top': this.options.top,
			'left': this.options.left,
			'width': this.options.width,
			'height': this.options.height,
			'background': "transparent"
		};
		
		/** An element was not found */
		if(!$defined(this.element)){
			this.options.id = "tagCloudAtr";
			this.element = new Element('div', {
				'id': this.options.id,
				'styles': styles
			}).inject(Body);
		} else {
			this.element.setStyles(styles);
		}

		/* Updating the dimensions in case of non 'absolute' positioning */
		this.setOptions($(this.element).getCoordinates());
		
		var size = this.options;
		var mouse = this.mouse;
		/** Updates the mouse position */
		this.element.addEvent('mousemove', function(event){
			mouse.set(
				((-event.page.y + size.top)*2/size.height + 1)*1.8,
				((event.page.x - size.left)*2/size.width - 1)*1.8
			);
		});

		var atr = this;
		/** Tells the cloud the mouse is (not) in range */
		this.element.addEvent("mouseenter", function(){
			atr.active = true;
		});

		this.element.addEvent("mouseleave", function(){
			atr.active = false;
		});
	},

	active: false
});

/** Emulation of static variables. */
var __TagId = 0;
function getTagId(){
	__TagId += 1;
	return __TagId;
}

/** The Tag Class
	TODO: Changeable way of display, eg. image.
*/
var Tag = new Class({
	/** Rank is a value from <0, 100> or <0, 1> */
    initialize: function(title, rank, url){
    	this.id = getTagId();
        this.title = title;
        
        this.rank = ($defined(rank) && rank >=0 ) ? rank : 30;
        if(this.rank > 1) this.rank /= 100;
        this.rank = Math.min(this.rank, 1);
        
        this.url = $pick(url, "#")

		/** Creating the Tag */	
		this.element = new Element('a', {
			'id': "stratusTag" + this.id,
			'href': this.url,
			'class': 'tagClass',
			'html': this.title,
			'styles': {
				'font-size': this.rank * SizeRange + Config.Smallest + 'pt'
			}
		}).inject(Body);

		this.fx = new Fx.Morph(this.element, {
			duration: Config.AnimationTime,
			transition: Fx.Transitions.Linear
		});
    },
    
    position : new Vector()
});


/** The Tag Cloud Class
*/
var TagCloud = new Class({
	initialize: function(width, height, uniform){
		width = width || 400;
		height = height || 200;
		
		this.radius = $pick(Math.min(width, height)/4.5, Config.Radius);
		this.uniform = $pick(uniform, true);

		if(true){
			this.items = new Array(
				new Tag("Stratus", 100),
				new Tag("Wp-Cumulus", 80),
				new Tag("MooTools", 80),
				new Tag("JQuery", 40),
				new Tag("Może kiedyś WordPress?", 40),
				new Tag("Web design"),
				new Tag("Roy Tanck", 100, "http://www.roytanck.com"),
				new Tag("Dawid Fatyga", 70, "http://student.agh.edu.pl/~fatyga"),
				new Tag("Wolfgang St&#x00F6;hler", 100, "http://www.loeffel-gegen-loeffel.de/rezepte"),
				new Tag("WordPress"),
				new Tag("MediaWiki"),
				new Tag("Pascal"),
				new Tag("C / C++"),
				new Tag("Python"),
				new Tag("Java Script"),
				new Tag("Java"),
				new Tag("PHP")
			);
		} else {
			this.items = new Array();
			(50).times(function(i){
				this.items[i] = new Tag(".", 100);
			}, this);
		}

		var style = ".tagClass { position: absolute; padding: 3px; color: " + Config.Color + "; } " +
					".tagClass:hover { text-decoration: none; color: " + Config.Hover + "; border: solid 1px "+ Config.Hover + "; }";
					
		/** Creating the Tag Style */
		if(!Browser.webkit) new Element('style', { 'html': style }).inject(document.head, 'top');	/** Every browser apart from IE and Chrome */
		else if(!Browser.trident) $$("style")[0].appendText(style); 								/** Chrome */
		
		this.atractor = new Atractor({
			id: "atractor",
			width: width,
			height: height
		});
		this.position = new Vector(
			this.atractor.options.left + this.atractor.options.width /2 - width/4,
			this.atractor.options.top + this.atractor.options.height /2 - height/16
		);
		this.distribute();
	},
	
	delta : new Vector(-2, -2),
	
	/** Seting up the initial tag positions */
	distribute : function(){
		var p = 0, t = 0, i = 1,
			count = this.items.length,
			radius = this.radius,
			tags = this.items;

		while(i <= count){
			if(this.uniform){
				p = Math.acos(-1+(2*i-1)/count);
				t = Math.sqrt(count * Math.PI) * p;
			} else {
				p = Math.random() * (Math.PI);
				t = Math.random() * (2*Math.PI);
			}
			tags[i-1].position.set(
				radius * Math.cos(t) * Math.sin(p),
				radius * Math.sin(t) * Math.sin(p),
				radius * Math.cos(p)
			);
			i += 1;
		}
		this.update();
	},

	/** Updating the tags document positions */
	update: function(delta){
		var delta = $pick(delta, ZeroVector),
			tags = this.items,
			a = $pick(delta.x, 0), b = $pick(delta.y, 0),
			sinA = sine(a), cosA = cosine(a),
			sinB = sine(b), cosB = cosine(b);

		tags.each(function(item){	
			var pos = item.position,
				x1 = pos.x,
				y1 = pos.y * cosA + pos.z * (-sinA),
				z1 = pos.y * sinA + pos.z *   cosA;
				
			var x = x1 * cosB + z1 * sinB,
				y = y1,
				z = x1 * (-sinB) + z1 * cosB;
			pos.set(x, y, z);
								  
			var per = Config.Depth / (Config.Depth + z);
			if(Config.UseFx){
				item.fx.start({
					"left": this.position.x + x * per,
					"top": this.position.y + y * per,
					"opacity": Math.max(per-0.7, 0)/0.5 + 0.2,
					"font-size": (item.rank * per * SizeRange * 2 + Config.Smallest) + "pt"
				});
			} else {			
				item.element.setStyles({
					"left": this.position.x + x * per,
					"top": this.position.y + y * per,
					"opacity": Math.max(per-0.7, 0)/0.5 + 0.2,
					"font-size": (item.rank * per * SizeRange + Config.Smallest) + "pt"
				});
			}
		}, this);	
	},

	pause: function(){
		if($defined(this.animation))
			$clear(this.animation);
	},
	
	/** Animate the tags */
	animate: function(){
		this.animation = (function(){
			if(this.atractor.active) {
				var vec = this.atractor.mouse;
				this.delta = vec;
			} else var vec = this.delta.mult(0.98);
			if(vec.longEnough()) this.update(vec);
		}).periodical(Config.AnimationTime, this);
	}
});

invokeLater(function() {
	try {
		new TagCloud(600, 400).animate();
	} catch(err){
		alert(err);
	}
});

