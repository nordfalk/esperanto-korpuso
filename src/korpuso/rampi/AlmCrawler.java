package korpuso.rampi;

import websphinx.Crawler;
import websphinx.Page;
import websphinx.Link;
import websphinx.LinkPredicate;
import websphinx.DownloadParameters;
import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashSet;

// eo.EoCrawler
public class AlmCrawler extends Crawler
{
  int antSider=0;
  Runtime rt = Runtime.getRuntime();

  public void visit(Page page)
  {
    System.out.println(antSider++ +" " + page.getURL()
                       + " at" + this.getActiveThreads()
                       + " lt" + this.getLinksTested()
                       + " pl" + this.getPagesLeft()
                       + " tm" + rt.totalMemory()/10000000.0
                       + " fm" + rt.freeMemory()/10000000.0
        );
    page.discardContent();
    page.getOrigin().setPage(null);
  }



  public static void main(String[] args) throws Exception
  {
    AlmCrawler c = new AlmCrawler();
    /*
      c.setRoots(new Link[] {
//                 new Link("http://localhost/doc/"),
                 new Link("http://www.stelaro.by.ru/vejc/esp/eventoj_novjarfesto.html"),
                 new Link("http://www.geocities.com/agrao99/gandhi/eksperimentoj/unua/infanagxo.htm"),
                 new Link("http://sezonoj.itgo.com/H-2000ak.htm"),
                 new Link("http://www.stelaro.by.ru/vejc/esp/eventoj_novjarfesto.html"),
                 new Link("http://www.bongo.ne.jp/~teg/nisitama/midori/midori1-6.htm"),

        new Link("http://pingo.cv.ihk.dk/"),
                         new Link("http://javabog.dk/"),
                         new Link("http://javabog.dk:8080/JSP"),
                         new Link("http://javabog.dk/ILB/"),
                 new Link("http://javabog.dk:8080/JSP/kode"),
      });
     */

      /**/
      XMLDecoder x = new XMLDecoder(new FileInputStream("pagxoj.xml"));
      ArrayList urloj = new ArrayList((HashSet) x.readObject());
      x.close();
      Link[] l = new Link[urloj.size()];
      for (int i=0; i<urloj.size(); i++) l[i] = new Link((String) urloj.get(i));
      c.setRoots(l);
      /**/

      //c.addClassifier();
      c.setLinkType(c.WEB);
      c.setDownloadParameters( DownloadParameters.DEFAULT.changeMaxThreads(1));
      c.setMaxDepth(10);
      //c.setSynchronous(true);
      //c.setDepthFirst(true);
      c.run();
    }
}
