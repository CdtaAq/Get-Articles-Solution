import java.io.*;
import java.net.*;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

class Result {

```
public static List<String> getArticleTitles(String author) {
List<String> titles = new ArrayList<>();

try {
int page = 1;
int totalPages = 1;

// Keep fetching pages until we've processed all pages
while (page <= totalPages) {
String urlString = "https://jsonmock.hackerrank.com/api/articles?author=" +
URLEncoder.encode(author, "UTF-8") + "&page=" + page;

URL url = new URL(urlString);
HttpURLConnection conn = (HttpURLConnection) url.openConnection();
conn.setRequestMethod("GET");

BufferedReader reader = new BufferedReader(
new InputStreamReader(conn.getInputStream())
);

StringBuilder response = new StringBuilder();
String line;
while ((line = reader.readLine()) != null) {
response.append(line);
}
reader.close();

// Parse JSON response
ObjectMapper mapper = new ObjectMapper();
JsonNode rootNode = mapper.readTree(response.toString());

// Update total pages from the first response
if (page == 1) {
totalPages = rootNode.get("total_pages").asInt();
}

// Process articles in the data array
JsonNode dataArray = rootNode.get("data");
for (JsonNode article : dataArray) {
String title = null;
String storyTitle = null;

// Get title and story_title, handling null values
if (article.has("title") && !article.get("title").isNull()) {
title = article.get("title").asText();
}
if (article.has("story_title") && !article.get("story_title").isNull()) {
storyTitle = article.get("story_title").asText();
}

if (title != null) {
titles.add(title);
} else if (storyTitle != null) {
titles.add(storyTitle);
}
// If both are null, ignore the article
}

page++;
}

} catch (Exception e) {
e.printStackTrace();
}

return titles;
}
```

}
