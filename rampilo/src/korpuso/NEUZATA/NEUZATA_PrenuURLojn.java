package korpuso.NEUZATA;
import java.beans.*;
import java.io.*;
import java.util.*;


public class NEUZATA_PrenuURLojn
{
	public static void main(String[] args) throws Exception
	{
		HashSet urloj = new HashSet();
		/*
		urloj.add("data/19a-rap-Claude.html");
		urloj.add("data/H-ejl.htm");
		urloj.add("data/kanto_karteta.htm");
		urloj.add("data/komenci_fini_daurigi_chesi.html");
		urloj.add("data/rclingvoj.html");
		*/


		XMLDecoder x = new XMLDecoder(new FileInputStream("data/pagxoj.xml"));
		urloj = (HashSet) x.readObject();

		ArrayList frazoj = new ArrayList();
		//frazoj.add("Ŝi ankoraŭ povas rideti, almenaŭ kiam ŝi ne staras sur ĉiuj kvar kaj spiras dum naskodoloro!");

		OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("data/frazoj.txt"));//,"UTF-8");

		for (Iterator it=urloj.iterator(); it.hasNext(); )
		{
			String url = (String) it.next();
			System.out.println(" ---------- "+url+" ------------ ");

			Process p = Runtime.getRuntime().exec("python html2text-2.01a_jacob.py "+url);
			BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(),"UTF-8"));

			String lin = null;
			while ( (lin=br.readLine()) != null)
			{
				lin = lin.trim();
				if (lin.length()==0) continue;

				lin = lin.replaceAll("ĉ","cx");
				lin = lin.replaceAll("ĝ","gx");
				lin = lin.replaceAll("ĥ","hx");
				lin = lin.replaceAll("ĵ","jx");
				lin = lin.replaceAll("ŝ","sx");
				lin = lin.replaceAll("ŭ","ux");

				lin = lin.replaceAll("Ĉ","CX");
				lin = lin.replaceAll("Ĵ","JX");
				lin = lin.replaceAll("Ĝ","GX");
				lin = lin.replaceAll("Ĥ","HX");
				lin = lin.replaceAll("Ŝ","SX");
				lin = lin.replaceAll("Ŭ","UX");


				//System.out.println("Ŝi ankoraŭ povas rideti, almenaŭ kiam ŝi ne staras sur ĉiuj kvar kaj spiras dum naskodoloro!");
				System.out.println(lin);
				frazoj.add(lin);
				osw.write(lin);
				osw.write("\n");
			}
			p.waitFor();
		}

		FileOutputStream fos = new FileOutputStream("data/frazoj.xml");
		XMLEncoder x2 = new XMLEncoder(fos);
		x2.writeObject(frazoj);
		x2.close();

		for (int j=0; j<frazoj.size(); j++)
		{
		}
		osw.close();
	}
}
