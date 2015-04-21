# Tio ĉi projekto maljuniĝis #
Por vidi la aktualajn Esperanto-korpusojn vi pli bone rigardu:

  * http://eo.wikipedia.org/wiki/Korpuso
  * http://wiki.apertium.org/wiki/Vikipedia_korpuso_de_Esperanto


# Malnova teksto #
Por konstrui bonajn lingvistikajn ilojn, kiel ekz. vortlistoj, vortaroj k.t.p. bezonatas korpuson; kolekto de tekstoj en la lingvo.

Tiu ĉi projekto enhavas ilojn por fari korpuson en Esperanto (sed la principo kaj la kodo verŝajne utilas ankaŭ al aliaj malgrandaj lingvoj):

A) Serĉmaŝino (kiu uzas Guglo serĉ-API) kiu trovas retpaĝojn en Esperanto kaj konstruas komencan liston de adresoj (URLoj).

B) Interreta rampilo (angle: web crawler - nun ni uzas WebSPHINX sed eble estonte Heritrix) por trarampi kaj amasigi hejmpaĝojn en Esperanto. Se ne-Esperanta paĝo estas trafita ĝi estas forĵetita.

C) Stokaj aŭ procesaj moduloj por la korpuso estos eble aldonota.

Plejparto de ka kodo estas en Javo sed kelkaj partoj estas en Perlo kaj Ruby.

La projekto estas nun uzata de:
- http://corp.hum.sdu.dk/cqp.eo.html  (http://visl.sdu.dk/)

Estonte ni esperas ke ankaŭ la sekvaj projektoj povas utiligi la laboron:
- 'OFTECA  VORTARO  DE  ESPERANTO' (http://www.esperanto.freenet.tj/vortaroj/oftec.htm)

# Aliaj eblecoj fari korpuson #

- the free BootCat tools (skribita en Perl):  http://sslmit.unibo.it/~baroni/bootcat.html

## Crúbadán ##
Corpus building for minority languages - http://borel.slu.edu/crubadan/.
La aŭtoro, Kevin, verŝajnoe povos (somere 2008) produkti korpuson de 30 milionoj da vortoj por ni

# English resume #
To build good language tools, such as word list and dictionaries  you need a corpus; a large collection of texts in the language.

This project consists of tools to generate an Esperanto corpus (but the principle and the code will probably work just fine for other smaller languages as well):

A) A search engine (that uses Google search API) that finds web pages in Esperanto and generates an initial list of URLs.

B) A web crawler (that currently uses WebSPHINX but we might change to Heritrix) to crawl the web to build up a corpus of Esperanto web pages. If a non-Esperanto page is detected it is left out of the crawl.

C) Storing or processing modules for the corpus might be added later.

Most the code is written in Java but some optional pieces are written in Perl and Ruby.