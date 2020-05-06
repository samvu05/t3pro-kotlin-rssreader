package com.sam.t3prorsskl

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.util.Xml
import androidx.appcompat.app.AppCompatActivity
import org.w3c.dom.Element
import org.xmlpull.v1.XmlPullParser
import java.net.URL
import javax.xml.parsers.DocumentBuilderFactory


class MainActivity : AppCompatActivity() {
    private var rssData = mutableListOf<RssData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createAsy()
    }

    inner class createAsy : AsyncTask<String,Void,Boolean>() {
        override fun doInBackground(vararg params: String?): Boolean {
            readData()
            return true;
        }
    }

    private fun readData(){
        val link = "https://cdn.24h.com.vn/upload/rss/trangchu24h.rss"
        val inP  = URL(link).openConnection()?.getInputStream();
        val buiderFactory = DocumentBuilderFactory.newInstance();
        val docBuider = buiderFactory.newDocumentBuilder();
        val doc = docBuider.parse(inP)
        val listNot = doc.getElementsByTagName("item")
        for (i in 0..listNot.length-1){
            val node = listNot.item(i) as Element
            val title = node.getElementsByTagName("title").item(0).textContent
            val pubDate = node.getElementsByTagName("pubdate").item(0).textContent
            val description = node.getElementsByTagName("description").item(0).textContent
            val link = ((node.getElementsByTagName("description").item(0) as Element)
                .getElementsByTagName("img").item(0) as Element).getAttribute("src")
            rssData.add(
                RssData(title,description,link)
            )

        }

    }
}
