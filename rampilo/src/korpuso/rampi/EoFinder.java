package korpuso.rampi;

import java.io.*;

public class EoFinder
{
  private Process p;
  private BufferedReader r;
  private PrintWriter w;

  public EoFinder() {
    try {
      p = Runtime.getRuntime().exec("./esponly");
      r = new BufferedReader(new InputStreamReader(p.getInputStream()));
      w = new PrintWriter((p.getOutputStream()));
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public synchronized boolean erEo(String linje) {
    try {
      // Fjern alle specielle tegn før der skrives til processen
      linje = linje.replaceAll("[^\\p{Graph}]", " ");
      w.println(linje);
      w.flush();
      String svar = r.readLine();
      boolean erEo = (svar.charAt(0) == 'j');
      return erEo;
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    return false;
  }


  public static void main(String[] args)
  {
    EoFinder eofinder = new EoFinder();
  }
}
