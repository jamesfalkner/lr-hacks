create table Templates_FakeService (
	message VARCHAR(75) null,
	messageId LONG not null primary key
);

create table Templates_Note (
	name VARCHAR(75) null,
	note VARCHAR(75) null,
	noteId LONG not null primary key
);

create table Templates_Something (
	message VARCHAR(75) null,
	someId LONG,
	messageId LONG not null primary key
);