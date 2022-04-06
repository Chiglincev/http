import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.List;

public class Main {

    private final static String URI = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";

    public static void main(String[] args) throws IOException {

        CloseableHttpClient httpClient = createHttpClient();
        HttpGet request = new HttpGet(URI);
        CloseableHttpResponse response = httpClient.execute(request);

        ObjectMapper mapper = new ObjectMapper();
        List<Fact> factList = mapper.readValue(response.getEntity().getContent(), new TypeReference<List<Fact>>() {});

        List<Fact> filtredFactList = factList.stream()
                .filter(fact -> fact.getUpvotes() != null && Integer.parseInt(fact.getUpvotes()) > 0)
                .toList();

        filtredFactList.forEach(System.out::println);
    }

    static CloseableHttpClient createHttpClient() {
        return HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();
    }
}
