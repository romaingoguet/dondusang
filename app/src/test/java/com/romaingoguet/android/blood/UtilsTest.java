package com.romaingoguet.android.blood;

import com.romaingoguet.android.blood.utils.Utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;

public class UtilsTest {

    @Test
    public void transformDateToString() {
        Calendar cal1 = Calendar.getInstance();
        cal1.set(2019, 1, 1);
        Calendar cal2 = Calendar.getInstance();
        cal2.set(1986, 10, 30);
        Calendar cal3 = Calendar.getInstance();
        cal3.set(2100, 5, 5);
        Calendar cal4 = Calendar.getInstance();
        cal4.set(2018, 11, 24);
        Assert.assertEquals("01/02/2019", Utils.calendarDateToString(cal1));
        Assert.assertEquals("30/11/1986", Utils.calendarDateToString(cal2));
        Assert.assertEquals("05/06/2100", Utils.calendarDateToString(cal3));
        Assert.assertEquals("24/12/2018", Utils.calendarDateToString(cal4));
    }

    @Test
    public void transformStringToDate() {
        String date1 = "01/02/2019";
        String date2 = "30/11/1986";
        String date3 = "05/06/2100";
        String date4 = "24/12/2018";
        Calendar date = Calendar.getInstance();
        date.set(2019, 1, 1);
        date.compareTo(Utils.stringDateToCalendar(date1));
        date.set(1986, 10, 30);
        date.compareTo(Utils.stringDateToCalendar(date2));
        date.set(2100, 5, 5);
        date.compareTo(Utils.stringDateToCalendar(date3));
        date.set(2018, 11, 24);
        date.compareTo(Utils.stringDateToCalendar(date4));
    }

    @Test
    public void cleanHtmlBrText() {
        String string1 = "Lorem Ipsum <br />Lorem Ipsum";
        String string2 = " Lorem Ipsum <br /><br /> Lorem Ipsum";
        String resultString1 = "Lorem Ipsum Lorem Ipsum";
        String resultString2 = "Lorem Ipsum  Lorem Ipsum";
        Assert.assertEquals(resultString1, Utils.clean(string1));
        Assert.assertEquals(resultString2, Utils.clean(string2));
    }

    @Test
    public void getNewsType() {
        String string1 = "https://dondesang.efs.sante.fr/lefs-se-modernise-et-lance-le-projet-innovadon-2020";
        String string2 = "https://twitter.com/EFS_Officiel";
        String string3 = "https://www.facebook.com/EtablissementFrancaisduSang/";
        String string4 = "https://www.youtube.com/watch?v=GLWJanX_dyk";
        String reponse1 = Utils.EFS_ARTICLE_TYPE + "lefs se modernise et lance le projet innovadon 2020";
        String reponse2 = Utils.EFS_TWITTER_TYPE;
        String reponse3 = Utils.EFS_FACEBOOK_TYPE;
        String reponse4 = Utils.EFS_YOUTUBE_TYPE;
        Assert.assertEquals(reponse1, Utils.getNewsType(string1));
        Assert.assertEquals(reponse2, Utils.getNewsType(string2));
        Assert.assertEquals(reponse3, Utils.getNewsType(string3));
        Assert.assertEquals(reponse4, Utils.getNewsType(string4));
    }


}
