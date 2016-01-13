# EDS-IIT
test signing file using EUSignJava.jar
# OUTPUT
true
IIT EU Test[13 січ. 2016 21:58:47] Enumerating key medias...
IIT EU Test[13 січ. 2016 21:58:47] 0. гнучкий диск
IIT EU Test[13 січ. 2016 21:58:47] 1. з'ємний диск
IIT EU Test[13 січ. 2016 21:58:47] 2. оптичний диск
IIT EU Test[13 січ. 2016 21:58:47] 	0. E:\
IIT EU Test[13 січ. 2016 21:58:47] 3. е.ключ ІІТ Алмаз-1К
IIT EU Test[13 січ. 2016 21:58:47] 4. е.ключ ІІТ Алмаз-1К (носій)
IIT EU Test[13 січ. 2016 21:58:47] 5. е.ключ ІІТ Кристал-1
IIT EU Test[13 січ. 2016 21:58:47] 6. е.ключ ІІТ Кристал-1 (носій)
IIT EU Test[13 січ. 2016 21:58:47] 7. файлова система (каталоги системи)
IIT EU Test[13 січ. 2016 21:58:47] 8. файлова система (каталоги користувача)
IIT EU Test[13 січ. 2016 21:58:47] 9. е.ключ Aladdin eToken (PKCS#11, носій)
IIT EU Test[13 січ. 2016 21:58:47] 10. е.ключ чи смарт-карта Автор (PKCS#11, носій)
IIT EU Test[13 січ. 2016 21:58:47] 11. е.ключ чи с.-карта G&D SafeSign (PKCS#11, носій)
IIT EU Test[13 січ. 2016 21:58:47] 12. е.ключ SafeNet iKey (PKCS#11, носій)
IIT EU Test[13 січ. 2016 21:58:47] 13. е.ключ чи с.-карта Aladdin JaCarta (PKCS#11, носій)
IIT EU Test[13 січ. 2016 21:58:47] 14. е.ключ чи смарт-карта Автор (PKCS#11)
IIT EU Test[13 січ. 2016 21:58:47] 15. е.ключ чи смарт-карта Avest (PKCS#11, носій)
IIT EU Test[13 січ. 2016 21:58:47] 16. е.ключ чи смарт-карта Avest (PKCS#11)
IIT EU Test[13 січ. 2016 21:58:47] 17. е.ключ ІІТ Кристал-1 (PKCS#11, носій)
IIT EU Test[13 січ. 2016 21:58:47] 18. е.ключ ІІТ Кристал-1 (PKCS#11)
IIT EU Test[13 січ. 2016 21:58:47] 19. е.ключ ІІТ Алмаз-1К (PKCS#11, носій)
IIT EU Test[13 січ. 2016 21:58:47] 20. е.ключ ІІТ Алмаз-1К (PKCS#11)
IIT EU Test[13 січ. 2016 21:58:47] 21. е.ключ Aladdin JaCarta ASE (PKCS#11, носій)
IIT EU Test[13 січ. 2016 21:58:47] 22. смарт-карта Техноконс. TEllipse
IIT EU Test[13 січ. 2016 21:58:48] 23. смарт-карта Техноконс. TEllipse2/3
IIT EU Test[13 січ. 2016 21:58:48] Enter key media type index:
2
IIT EU Test[13 січ. 2016 21:58:53] Enter key media device index:
0
IIT EU Test[13 січ. 2016 21:58:54] Enter key media password:
password
IIT EU Test[13 січ. 2016 21:58:59] Reading private key...
java.lang.Exception: Сертифікат не знайдено
	at com.iit.certificateAuthority.endUser.libraries.signJava.EndUser.RaiseError(EndUser.java:169)
	at com.iit.certificateAuthority.endUser.libraries.signJava.EndUser.ReadPrivateKeySilently(EndUser.java:330)
	at com.softserveinc.edu.ita.EndUserSign.readPKey(EndUserSign.java:166)
	at com.softserveinc.edu.ita.EndUserSign.signingAndVerifyingFile(EndUserSign.java:182)
	at com.softserveinc.edu.ita.Main.main(Main.java:13)
