package korpuso.rampi;

import java.beans.*;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.util.regex.Pattern;

import javax.swing.text.*;
import javax.swing.text.html.*;

import websphinx.*;

/*
 http://www.manageability.org/blog/stuff/open-source-web-crawlers-java



 -rw-------  1 j j   2116234 okt 23 11:35 host_7689_bertilow_com.dat
 -rw-------  1 j j    747068 okt 23 11:35 host_7689_bertilow_com.txt
 -rw-------  1 j j     10568 okt 23 11:35 host_7689_bertilow_com.url
 -rw-------  1 j j     57887 okt 23 10:01 host_7689_www_ameriko_org.dat
 -rw-------  1 j j     34812 okt 23 10:01 host_7689_www_ameriko_org.txt
 -rw-------  1 j j       239 okt 23 10:01 host_7689_www_ameriko_org.url
 -rw-------  1 j j     99160 okt 23 10:00 host_7689_www_stelaro_by_ru.dat
 -rw-------  1 j j     45536 okt 23 10:00 host_7689_www_stelaro_by_ru.txt
 -rw-------  1 j j       393 okt 23 10:00 host_7689_www_stelaro_by_ru.url
 -rw-------  1 j j     22114 okt 23 09:59 host_7689_pub45_bravenet_com.dat
 -rw-------  1 j j      4890 okt 23 09:59 host_7689_pub45_bravenet_com.txt
 -rw-------  1 j j        70 okt 23 09:59 host_7689_pub45_bravenet_com.url
 -rw-------  1 j j   1894051 okt 23 09:59 host_7689_www_geocities_com.dat
 -rw-------  1 j j    831521 okt 23 09:59 host_7689_www_geocities_com.txt
 -rw-------  1 j j     11459 okt 23 09:59 host_7689_www_geocities_com.url
 -rw-------  1 j j     87408 okt 23 09:59 host_7689_iej_esperanto_it.dat
 -rw-------  1 j j     56165 okt 23 09:59 host_7689_iej_esperanto_it.txt
 -rw-------  1 j j       178 okt 23 09:59 host_7689_iej_esperanto_it.url

 nummeret (her 7689) er bare et unikt nummer (for at kunne skelne kørslerne)
 .url er de URL-adresser der er blevet hentet fra den server.
 .dat er de oprindelige sider (HTML)
 .txt er teksten renset for HTML og konverteret til X-notation.



 Lige hurtigt navnet på den fil, der bliver næsten tom i .txt: host_9233_membre_lycos_fr.txt

 De andre taber normalt 15-20%, Vikipedio dog 60% (?).


      // XXX
      // pdftotext ind til at konvertere PDF-dokumenter

      // XXX Gemme søgnings-front og besøgte sider og starte igen senere?

*/

// eo.EoCrawler
public class EoCrawler extends Crawler
{
  private static final long serialVersionUID = -3757789861955555450L;


  transient HTMLEditorKit kit = new HTMLEditorKit();
  transient EoFinder finder = new EoFinder();
  static final String ESPERANTO = "esperanto";
  int unikt_id = (int) (10000*Math.random());
  String unik_mappe = "data_"+unikt_id;

  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
  {
    in.defaultReadObject();
    kit = new HTMLEditorKit();
    finder = new EoFinder();
    rt = Runtime.getRuntime();
  }

  public static String htmlPrækonverteting(String html) {
    html = html.replaceAll("<[iI]>","«");
    html = html.replaceAll("</[iI]>","»");
    html = html.replaceAll("<[eE][mM]>","«");
    html = html.replaceAll("</[eE][mM]>","»");
    html = html.replaceAll("(<[hH][1234]>)","¶$1");
    html = html.replaceAll("(</[hH][1234])>","$1¶");
    return html;
  }

  public static String tekstPostkonvertering(String tekst)
  {
    tekst = tekst.replaceAll("\u0109", "cx");
    tekst = tekst.replaceAll("\u011D", "gx");
    tekst = tekst.replaceAll("\u0125", "hx");
    tekst = tekst.replaceAll("\u0135", "jx");
    tekst = tekst.replaceAll("\u015D", "sx");
    tekst = tekst.replaceAll("\u016D", "ux");

    tekst = tekst.replaceAll("\u0108", "Cx");
    tekst = tekst.replaceAll("\u0134", "Jx");
    tekst = tekst.replaceAll("\u011C", "Gx");
    tekst = tekst.replaceAll("\u0124", "Hx");
    tekst = tekst.replaceAll("\u015C", "Sx");
    tekst = tekst.replaceAll("\u016C", "Ux");

    tekst = tekst.replaceAll("\\^c", "cx");
    tekst = tekst.replaceAll("\\^g", "gx");
    tekst = tekst.replaceAll("\\^j", "jx");
    tekst = tekst.replaceAll("\\^h", "hx");
    tekst = tekst.replaceAll("\\^s", "sx");
    tekst = tekst.replaceAll("[\\^\\~]u", "ux");

    tekst = tekst.replaceAll("\\^C", "Cx");
    tekst = tekst.replaceAll("\\^G", "Gx");
    tekst = tekst.replaceAll("\\^H", "Hx");
    tekst = tekst.replaceAll("\\^J", "Jx");
    tekst = tekst.replaceAll("\\^S", "Sx");
    tekst = tekst.replaceAll("[\\^\\~]U", "Ux");

    tekst = tekst.replaceAll("c'([a-z])", "cx$1");
    tekst = tekst.replaceAll("g'([a-z])", "gx$1");
    tekst = tekst.replaceAll("j'([a-z])", "hx$1");
    tekst = tekst.replaceAll("j'([a-z])", "jx$1");
    tekst = tekst.replaceAll("s'([a-z])", "sx$1");
    tekst = tekst.replaceAll("u'", "ux");

    tekst = tekst.replaceAll("C'([a-z])", "Cx$1");
    tekst = tekst.replaceAll("G'([a-z])", "Gx$1");
    tekst = tekst.replaceAll("H'([a-z])", "Hx$1");
    tekst = tekst.replaceAll("J'([a-z])", "Jx$1");
    tekst = tekst.replaceAll("S'([a-z])", "Sx$1");
    tekst = tekst.replaceAll("U'", "Ux");

    tekst = tekst.replaceAll("aù", "aux");
    tekst = tekst.replaceAll("Aù", "Aux");
    tekst = tekst.replaceAll("AÚ", "AUx");

    tekst = tekst.replaceAll("aú", "aux");
    tekst = tekst.replaceAll("Aú", "Aux");
    tekst = tekst.replaceAll("AÚ", "AUx");

    tekst = tekst.replaceAll("“", "«"); //  Eckhard bruger dem senere
    tekst = tekst.replaceAll("”", "»"); //  Eckhard bruger dem senere
    return tekst;
  }



  public String htmlTilTekstSwing(String html) {
    try {
      Document doc = kit.createDefaultDocument();
      {
        // The Document class does not yet handle charset's properly.
        doc.putProperty("IgnoreCharsetDirective", Boolean.TRUE);
      }
      // Create a reader on the HTML content.

      //System.err.println(html);
      Reader rd = new StringReader(html);
      // Parse the HTML.
      kit.read(rd, doc, 0);
      return doc.getText(0, doc.getLength()).trim();
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
  }

  public static String htmlTilTekstPy(String html) {
    try {
      Process p = Runtime.getRuntime().exec("html2text-2.01a_jacob.py");
      OutputStream os = p.getOutputStream();
      os.write(html.getBytes("UTF-8"));
      os.close();
      BufferedReader br = new BufferedReader(new InputStreamReader(p.
          getInputStream(), "UTF-8"));

      StringBuffer sb = new StringBuffer(html.length());
      String lin = null;
      while ( (lin = br.readLine()) != null) {
        lin = lin.trim();
        if (lin.length() > 0) {
          sb.append(lin);
          sb.append('\n');
        }
        continue;
      }
      //p.waitFor();
      br.close();
      //p.destroy();
      return sb.toString();
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
  }

  public static String pageTilHtml(Page page) {
    String indkodning = page.getContentEncoding();

    Pattern findcharset = Pattern.compile("[cC][hH][aA][rR][sS][eE][tT]=\"?([-_\\p{Alnum}]*)");
    Matcher indkMat = findcharset.matcher(page.getContentType());
    if (indkMat.find()) {
      indkodning = indkMat.group(1);
    }

    indkMat = findcharset.matcher(page.getContent());
    if (indkMat.find()) {
      indkodning = indkMat.group(1);
    }

    if (indkodning==null) indkodning="ISO-8859-1";
    String html = "";


    try {
      html = new String(page.getContentBytes(),indkodning);
    } catch (Exception ex) {
     System.err.println("Indkodningsfejl for "+page.getURL()+" "+ex+", bruger UTF-8");
      //ex.printStackTrace();
      try {
        html = new String(page.getContentBytes(),"UTF-8");
      }
      catch (Exception ex2) {
        ex2.printStackTrace();
      }
    }

    return html;
  }


  public boolean shouldVisit (Link link) {
    return link.getSource().getLabel(ESPERANTO) != null
        && link.getURL().toString().indexOf("action=")==-1
        && link.getURL().toString().indexOf("http://gigablast.com/get")==-1
        //&& link.getURL().toString().indexOf("wikipedia.org")==-1
        ;
    //return true;
  }

  private void skrivUrl(String u) {
    try {
      urlfos.write( ( u+"\n").getBytes());
      urlfos.flush();
    }
    catch (Exception ex) {
      System.err.println("FEJL ved skrivning i URL.fil");
      ex.printStackTrace();
    }
  }


  int antSider=0;
  transient Runtime rt = Runtime.getRuntime();

  public void visit(Page page)
  {
    boolean eo = false;

    try {
      //System.out.println(antSider++ +" " + page.getURL());



      //System.err.println(page.getBase() + " : " + page.getContentType());
      //System.err.println(page.getBase() + " : " + page.getContentType() +  " : " + page.getContentEncoding());


      if (!page.getContentType().startsWith("text/html")) {
        skrivUrl("ej HTML: "+page.getURL());
        System.out.println("ej HTML: "+page.getURL()+" men "+page.getContentType());
        return;
      }

      String tekst="", html="";

      html = pageTilHtml(page);
      html = htmlPrækonverteting(html);
      tekst = htmlTilTekstSwing(html);
      tekst = tekstPostkonvertering(tekst);

      //System.out.println(tekst);
      if (finder.erEo(tekst))
      {
        page.setLabel(ESPERANTO);
        eo = true;
        antSider ++;
        if (antSider%100==0) System.out.println( "- "
                           + " at" + this.getActiveThreads()
                           + " lt" + this.getLinksTested()
                           + " pl" + this.getPagesLeft()
                           + " tm" + rt.totalMemory()/1000000.0
                           + " fm" + rt.freeMemory()/1000000.0
                           + " " + new Date()
            );

        synchronized (this) {
          skrivUrl(page.getURL().toString());


          FileOutputStream fos = null;
          try {
            String filnavn = unik_mappe + "/servilo_" +
                page.getURL().getHost().replace('.', '_');


            fos = new FileOutputStream(filnavn + ".txt", true);
            fos.write(tekst.getBytes("ISO-8859-1")); // ISO-8859-1  eller utf-8 ?
            fos.close();

            fos = new FileOutputStream(filnavn + ".dat", true);
            fos.write(page.getContentBytes());
            fos.write(0);
          }
          finally {
            fos.close();
            fos = null;
          }
          if (antSider%200==0) this.notify(); // Væk serialiseringstråden
        }
      }
      Thread.sleep(200); // Pæn opførsel :-)
    }
    catch (Exception ex) {
      System.err.println("FEJL for "+page.getURL());
      ex.printStackTrace();
    } finally {
      page.discardContent();
      page.getOrigin().setPage(null);
      page.getOrigin().discardContent();

      if (eo) System.out.println(antSider +" " + page.getURL()+" EO");
      else System.out.println(antSider +" " + page.getURL()+" %");
    }
  }

  transient FileOutputStream urlfos;

  public static void main(String[] args) throws Exception
  {
    EoCrawler c = null;
    if (args.length>0) {
      ObjectInputStream ois = new ObjectInputStream(new FileInputStream(args[0]));
      c = (EoCrawler) ois.readObject();
      ois.close();
      System.out.println("Besøgte sider: "+c.visitedPages);
    } else {
      c = new EoCrawler();

      if (new File(c.unik_mappe).exists()) throw new IOException("Mappen eksisterer allerede");
      new File(c.unik_mappe).mkdirs();

      XMLDecoder x = new XMLDecoder(new FileInputStream("pagxoj.xml"));
      ArrayList urloj = new ArrayList((HashSet) x.readObject());
      x.close();
      Link[] l = new Link[urloj.size()];
      for (int i=0; i<urloj.size(); i++) l[i] = new Link((String) urloj.get(i));
      c.setRoots(l);
    }

    c.urlfos = new FileOutputStream(c.unik_mappe + "/adresoj.url", true);
    //c.setLinkType(c.WEB);
    c.setLinkType(c.HYPERLINKS);
    c.setDownloadParameters( DownloadParameters.DEFAULT.changeMaxThreads(10).changeDownloadTimeout(120));
    //c.setSynchronous(true);
    c.setMaxDepth(10);
    c.setDepthFirst(false);

    new Thread(c).start();

    int sidstAntalSider = 0;

    while (true) synchronized (c) {
      c.wait(1000*60*10);
      //if (c.antSider >= sidstAntalSider+ 200)
      try {
        System.err.println("SERIALISERER!!!!! "+ new Date());
        sidstAntalSider = c.antSider;
        ObjectOutputStream ooo = new ObjectOutputStream(new FileOutputStream("eocrawl"+c.antSider+".ser"));
        ooo.writeObject(c);
        ooo.close();

        if (c.WEB!=null) System.out.println("WEB = " + Arrays.asList(c.WEB));
        if (c.SERVER!=null) System.out.println("SRV = " + Arrays.asList(c.SERVER));
        if (c.SUBTREE!=null) System.out.println("SUB = " + Arrays.asList(c.SUBTREE));
        if (c.HYPERLINKS!=null) System.out.println("LNK = " + Arrays.asList(c.HYPERLINKS));

      } catch (Exception e) {
        e.printStackTrace();
      }
      Thread.sleep(5000);
    }
  }
}
/*
 export DISPLAY=:9
 nohup java -Xmx200m -Xms200m -cp websphinx.jar:classes eo.EoCrawler

*/

/*
c.setLinkPredicate(new LinkPredicate() {
    public void connected (Crawler crawler) {}
    public void disconnected (Crawler crawler) {}
    public boolean shouldVisit (Link link) {
      //System.out.println("LP "+ link.getURL());
      //System.out.println("LP "+ link.getSource().getLabel(ESPERANTO));
      return link.getSource().getLabel(ESPERANTO) != null
          && link.getURL().toString().indexOf("action=")==-1
          && link.getURL().toString().indexOf("http://gigablast.com/get")==-1
          //&& link.getURL().toString().indexOf("wikipedia.org")==-1
          ;
      //return true;
    }
});
*/
