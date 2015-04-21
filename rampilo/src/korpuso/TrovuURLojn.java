package korpuso;

import com.google.soap.search.*;
import java.beans.*;
import java.io.*;
import java.util.*;

public class TrovuURLojn {

  public static void main(String[] args) throws Exception { //

    //String sercxvortoj = "ŝi li dum"; // args[1];
    //String sercxvortoj = "ŝi li dum"; // args[1];
    //String sercxvortoj = "dum la ĉi"; // args[1];
    String sercxvortoj = "kaj kiu kia"; // args[1];

//                if ($text =~ /[ \(\-=][a-z]*((ad|dev|dir|est|far|hav|igx?|pov|vol)[aiou]s[^a-z])/) {$esp =$esp + 10;} # E-verbs
//                if ($text =~ / (ambaux|ankaux|ankoraux|hieraux|ili|kaj|ke|laux|[ln]i|morgaux|pli|plu|sed)[^a-z]/) {$esp =$esp + 30;} # E-uninflectables, NOT 'aux'
//                if ($text =~ /[ \(](([kt]|cx|nen)?i([aeou]j?|[ao]m|al|es))[^a-z]/) {$esp =$esp + 30;} ; # tabel-words

    Date dato = new Date();

    if (args.length >= 1) {
      sercxvortoj = args[0];
    }

    GoogleSearch s = new GoogleSearch();
    //s.setKey("VLjZ1P5QFHICg5MfvjuwzmJm1T/iYbdY"); // Jacobs nøgle
    s.setKey("9rylX3JQFHK2wfVZSnrfT1gjmV75J2JF"); // Mads' nøgle
    s.setQueryString(sercxvortoj);
    s.setFilter(false);
    s.setSafeSearch(false);

    int indekso = 0;
    int fejl = 0;
    HashSet pagxoj = new HashSet();

    try {
      new File("data").mkdir();
      FileInputStream fis = new FileInputStream("data/pagxoj.xml");
      XMLDecoder x = new XMLDecoder(fis);
      pagxoj = (HashSet) x.readObject();
      fis.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    System.out.println("pagxoj = " + pagxoj.size());

    while (true)
      try {
        // Evt. kunne man concatinere flere argumenter til en søgestreng...
        s.setStartResult(indekso);
        GoogleSearchResult sr = s.doSearch();

        /*
            System.err.println("Google Search Results:");
            System.err.println("======================");
            System.err.println(sr.toString());
         */
        System.err.println("---------- " + indekso);

        GoogleSearchResultElement[] re = sr.getResultElements();
        for (int i = 0; i < re.length; i++) {
          //System.err.println(re[i].getURL() + " " + re[i].getTitle());
          if (pagxoj.contains(re[i].getURL())) {
            System.out.println(re[i].getURL());
//					System.out.println(re[i].getURL()+" jam trovigxas");
          }
          else {
            pagxoj.add(re[i].getURL());
            System.out.println(re[i].getURL());
          }
        }
        int fina_indekso = sr.getEndIndex();

        if (fina_indekso >= indekso)
          indekso = fina_indekso;
        else
          break;
      }
      catch (Exception e) {
        indekso++;
        fejl++;
        e.printStackTrace();
        if (fejl >= 10)
          break;
      }

    System.out.println("pagxoj = " + pagxoj.size());

    try {
      FileOutputStream fos = new FileOutputStream("data/pagxoj.xml");
      XMLEncoder x = new XMLEncoder(fos);
      x.writeObject(pagxoj);
      x.close();
      fos.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}

