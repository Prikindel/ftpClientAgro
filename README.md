# Парсер данных о погоде с сервера "Фобос" в локальную БД

Консольное приложение, получающее данные с сервера "Фобос" в txt файлах и загружающее их в локальную БД.<br>
Файлы содержат:<br>
- Строка с информацией о типе нижележащих данных
    - Строки с координатами и данными в ней
![Файл с данными](https://github.com/Prikindel/images/blob/main/gribrefcfobos.PNG)
<br>
Необходимые типы данных находятся в файле [/parser/FileConfig.kt](https://github.com/Prikindel/ftpClientAgro/blob/master/src/main/kotlin/parser/FileConfig.kt)
<br>
Данные с БД [/module/DBConfig.kt](https://github.com/Prikindel/ftpClientAgro/blob/master/src/main/kotlin/module/DBConfig.kt)
