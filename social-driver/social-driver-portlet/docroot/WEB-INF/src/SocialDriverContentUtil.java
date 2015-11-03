import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SocialDriverContentUtil {

// holds our random content (titles, bodies, tags)
private Map<String, ContentArticle> contentCache = null;
private String[] keys = null;

public SocialDriverContentUtil() {
}

// ensure content is populated before being used
public synchronized void contentInit() throws Exception {
    if (contentCache == null) {
        initializeContent();
    }
}

public String getRandomId() throws Exception {
    if (contentCache == null) {
        initializeContent();
    }
    return keys[(int) (Math.random() * (double) keys.length)];

}

public String getContentTitle(String id) throws Exception {
    contentInit();
    return contentCache.get(id).getTitle();
}

// retrieve specified content body
public String getContentBody(String id) throws Exception {
    contentInit();
    return contentCache.get(id).getBody();
}

// retrieve specified set of tags based on offset
public String[] getContentTags(String id) throws Exception {
    contentInit();
    return contentCache.get(id).getTags();

}

// retrieve content from wikipedia and insert into content containers
public void initializeContent() throws Exception {
    HttpClient httpclient = new HttpClient();
    contentCache = new HashMap<String, ContentArticle>();
    keys = new String[0];
    // search for liferay once so that we are guaranteed some results
    Set<ContentArticle> liferayArticles = fetchWikipediaArticles
        (httpclient, new String[]{"liferay"});
    for (ContentArticle article : liferayArticles) {
        contentCache.put(article.getTitle(), article);
        keys = ArrayUtil.append(keys, article.getTitle());
    }

    // search for some stuff
    for (int count = 0; count < SocialDriverConstants
        .WIKIPEDIA_FETCH_COUNT; count++) {
        String[] terms = getRandomTerms(SocialDriverConstants.FAVORED_TAGS);
        Set<ContentArticle> articles = fetchWikipediaArticles(httpclient,
            terms);
        for (ContentArticle article : articles) {
            contentCache.put(article.getTitle(), article);
            keys = ArrayUtil.append(keys, article.getTitle());
        }
    }
}

private static Set<ContentArticle> fetchWikipediaArticles(HttpClient
                                                              httpclient,
                                                          String[] terms)
    throws IOException, JSONException {
    int hitLength;
    Set<ContentArticle> result = new HashSet<ContentArticle>();

    StringBuilder sb = new StringBuilder();
    sb.append("http://en.wikipedia.org/w/api" +
        ".php?action=query&list=search&format=json&srsearch=");
    sb.append(HtmlUtil.escapeURL(StringUtil.merge(terms, ",")));
    sb.append("&srnamespace=0&srwhat=text&srprop=snippet&srlimit=");
    sb.append(SocialDriverConstants.WIKIPEDIA_MAX_ARTICLE_COUNT);

    GetMethod get = new GetMethod(sb.toString());
    httpclient.executeMethod(get);
    JSONObject wikis = JSONFactoryUtil.createJSONObject(get
        .getResponseBodyAsString());
    JSONArray qresult = wikis.getJSONObject("query").getJSONArray("search");
    hitLength = qresult.length();

    for (int k = 0; k < hitLength; k++) {
        JSONObject hit = qresult.getJSONObject(k);
        String title = hit.getString("title");
        String excerpt = hit.getString("snippet");
        if (excerpt == null || excerpt.isEmpty()) continue;
        String[] words = SocialDriverUtil.removeEnglishStopWords(title
            .replaceAll("[^A-Za-z0-9]", " ").split("\\s+"));
        result.add(new ContentArticle(title, excerpt, words));
    }
    return result;
}

// get some random terms to search for, with no duplicate terms
private String[] getRandomTerms(String[] allTerms) {

    int termCount = 1 + (int) (Math.random() *
        SocialDriverConstants.MAX_SEARCH_TERMS);
    List<String> result = new ArrayList<String>();
    String term;
    for (int j = 0; j < termCount; j++) {
        while (result.contains(term =
            SocialDriverUtil.getRndStr(allTerms))) {
            // do nothing
        }
        result.add(term);
    }
    return (String[]) result.toArray(new String[result.size()]);
}

private static class ContentArticle {
    String title, body;
    String[] tags;

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String[] getTags() {
        return tags;
    }

    private ContentArticle(String title, String body, String[] tags) {
        this.title = title;
        this.body = body;
        this.tags = tags;
    }
}
}
