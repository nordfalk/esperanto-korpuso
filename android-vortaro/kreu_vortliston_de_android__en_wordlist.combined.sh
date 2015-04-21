echo 'dictionary=main:eo,locale=eo,description=Esperanto,date=1402373178,version=47'

zcat /media/j/_data/LatinIME/dictionaries/en_wordlist.combined.gz | 
grep ' word='  |  # nur konsideri vortojn
perl -pe 's/ word=(.*),f=(.*)/\1,<word=\1%f=\2>/' | # formatigi al HTML
apertium -f html-noent en-eo | # traduki al Esperanto
grep -v '^,<word=' | # forpreni HTML-ecan formaton
grep -v '[*-]' | # forpreni vortojn nekonatajn de Apertium
perl -pe 's/(.*?),*<word=(.*)%(.*)>/ word=\1,\3/' |
perl -pe 's|(word=.*?) .*?,(.*)|\1,\2|' | # forpreni sekvajn vortojn se traduko donas pli ol unu
sort -u -t= -k2,2 |      # forprenu vortojn kiu aperas plurfoje - retenu la unuan kun la plej alta frekvenco
sort -r -g -t= -k3,3    # ordigu denove laŭ frekvencoj


