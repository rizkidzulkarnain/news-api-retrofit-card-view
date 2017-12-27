package com.tokopedia.test.generalclass;

import com.tokopedia.test.model.Article;
import com.tokopedia.test.model.ArticleResponse;
import com.tokopedia.test.model.News;
import com.tokopedia.test.model.SourceResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by dist-admin on 11/28/2017.
 */

public class config {
    public static News _News;
    public static SourceResponse _Sources;
    public static ArticleResponse _Articles;
    public static String _BaseURL = "https://newsapi.org/";
    public static String _BaseURL2 = "https://newsapi.org/v1/";
    public static String _ApiKey = "f5cfc1b6e66c487a8bc566acc51d41d6";

    public String sourceid = "";

    static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    static Date date = new Date();

    public static String _DateNow = (dateFormat.format(date).toString());

    public static parameter _param;
}
