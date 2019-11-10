package layout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.choisangah.realtonymony.R;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by choisangah on 2017. 6. 15..
 */

public class AppInfoFragment extends Fragment {

    ListView listView;
    ArrayList<String> data = new ArrayList<>();
    ArrayAdapter<String> adapter;
    Button goBlog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appinfo,container,false);
        listView = (ListView)(view).findViewById(R.id.bListView);
        goBlog = (Button)(view).findViewById(R.id.goBlog) ;
        adapter = new ArrayAdapter<String>(this.getContext(),android.R.layout.simple_list_item_1,data);
        listView.setAdapter(adapter);
        thread.start();
        moveToBlog();
        return view;
    }

    Handler handler = new Handler();
    Thread thread = new Thread(){
        @Override
        public void run() {
            try {
                URL url = new URL("http://blog.rss.naver.com/mjo1127.xml");
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                    int itemCount = readData(urlConnection.getInputStream());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                        }
                    });
                    urlConnection.disconnect();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    private int readData(InputStream inputStream) {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(inputStream);
            int datacount = parseDocument(document);
            return datacount;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int parseDocument(Document document) {
        Element docEle = document.getDocumentElement();
        NodeList nodelist = docEle.getElementsByTagName("item");
        int count = 0;
        if((nodelist != null) && (nodelist.getLength() > 0)){
            for(int i = 0; i < nodelist.getLength(); i++){
                String newsItem = getTagData(nodelist,i);
                if(newsItem != null){
                    data.add((newsItem));
                    count++;
                }
            }
        }
        return count;
    }

    private String getTagData(NodeList nodelist, int i) {
        String newsItem = null;
        try{
            Element entry = (Element)nodelist.item(i);
            Element title = (Element) entry.getElementsByTagName("title").item(0);
            Element pubDate = (Element) entry.getElementsByTagName("pubDate").item(0);

            String titleValue = null;
            if(title != null){
                Node firstChild = title.getFirstChild();
                if(firstChild != null) titleValue = firstChild.getNodeValue();
            }

            String pubDateValue = null;
            if(pubDate != null){
                Node firstChild = pubDate.getFirstChild();
                if(firstChild != null) pubDateValue = firstChild.getNodeValue();
            }

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
            Date date = new Date();
            newsItem = titleValue+"-"+simpleDateFormat.format(date.parse(pubDateValue));
        }
        catch (DOMException e){
            e.printStackTrace();
        }
        return  newsItem;
    }

    public void moveToBlog(){
        goBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://blog.naver.com/mjo1127"));
                startActivity(intent);
            }
        });
    }
}
