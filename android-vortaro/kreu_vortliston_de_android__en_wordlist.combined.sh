echo 'dictionary=main:eo,locale=eo,description=Esperanto,date=1402373178,version=47'

zcat /media/j/_data/android-src/packages/inputmethods/LatinIME/dictionaries/en_wordlist.combined.gz | 
grep ' word='  |  # nur konsideri vortojn
perl -pe 's/ word=(.*),f=(.*)/\1,<word=\1%f=\2>/' | # formatigi al HTML
apertium -f html-noent en-eo | # traduki al Esperanto
grep -v '^,<word=' | # forpreni HTML-ecan formaton
grep -v '[*-]' | # forpreni vortojn nekonatajn de Apertium
perl -pe 's/(.*?),*<word=(.*)%(.*)>/ word=\1,\3/' |
perl -pe 's|(word=.*?) .*?,(.*)|\1,\2|' | # forpreni sekvajn vortojn se traduko donas pli ol unu
(
echo '
 word=estas,f=210,flags=,originalFreq=222
 word=ĉu,f=200,flags=,originalFreq=222
 word=por,f=200,flags=,originalFreq=222
 word=pri,f=200,flags=,originalFreq=222
 word=oni,f=200,flags=,originalFreq=222
 word=li,f=200,flags=,originalFreq=222
 word=el,f=200,flags=,originalFreq=222
 word=ja,f=150,flags=,originalFreq=222
 word=kiuj,f=150,flags=,originalFreq=222
 word=diras,f=150,flags=,originalFreq=138
 word=da,f=150,flags=,originalFreq=222
 word=plu,f=150,flags=,originalFreq=222
 word=tion,f=150,flags=,originalFreq=222
 word=per,f=150,flags=,originalFreq=222
 word=sia,f=150,flags=,originalFreq=222
 word=sian,f=150,flags=,originalFreq=222
 word=iĝas,f=150,flags=,originalFreq=222
'
cat
) |
sort -u -t= -k2,2 |      # forprenu vortojn kiu aperas plurfoje - retenu la unuan kun la plej alta frekvenco
sort -r -g -t= -k3,3    # ordigu denove laŭ frekvencoj


