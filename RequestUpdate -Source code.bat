del /S D:\Work\4s\Requests Assembla Production\RequestsSource\RequestsSource\Requests\src\* /q
del /S D:\Work\4s\Requests Assembla Production\RequestsSource\RequestsSource\Requests\dbUpdates\* /q
del /S D:\Work\4s\Requests Assembla Production\RequestsSource\RequestsSource\Requests\web\* /q
del /S D:\Work\4s\Requests Assembla Production\RequestsSource\RequestsSource\Requests\WEB-INF\config\* /q
del /S D:\Work\4s\Requests Assembla Production\RequestsSource\RequestsSource\Requests\WEB-INF\tags\* /q
del /S D:\Work\4s\Requests Assembla Production\RequestsSource\RequestsSource\Requests\WEB-INF\lib\* /q
del /S D:\Work\4s\Requests Assembla Production\RequestsSource\RequestsSource\Requests\WEB-INF\classes\com\* /q

xcopy "C:\Program Files\Apache Software Foundation\Tomcat 8.5\webapps\Requests\src" "D:\Work\4s\Requests Assembla Production\RequestsSource\RequestsSource\Requests\src" /E /I /Y
xcopy "C:\Program Files\Apache Software Foundation\Tomcat 8.5\webapps\Requests\src\application" "D:\Work\4s\Requests Assembla Production\RequestsSource\RequestsSource\Requests\src\application" /E /I /Y
xcopy "C:\Program Files\Apache Software Foundation\Tomcat 8.5\webapps\Requests\src\application\*" "D:\Work\4s\Requests Assembla Production\RequestsSource\RequestsSource\Requests\src\application\*" /E /I /Y
xcopy "C:\Program Files\Apache Software Foundation\Tomcat 8.5\webapps\Requests\dbUpdates" "D:\Work\4s\Requests Assembla Production\RequestsSource\RequestsSource\Requests\dbUpdates" /E /I /Y
xcopy "C:\Program Files\Apache Software Foundation\Tomcat 8.5\webapps\Requests\web" "D:\Work\4s\Requests Assembla Production\RequestsSource\RequestsSource\Requests\web" /E /I /Y
xcopy "C:\Program Files\Apache Software Foundation\Tomcat 8.5\webapps\Requests\WEB-INF" "D:\Work\4s\Requests Assembla Production\RequestsSource\RequestsSource\Requests\WEB-INF" /I /Y
xcopy "C:\Program Files\Apache Software Foundation\Tomcat 8.5\webapps\Requests\WEB-INF\config" "D:\Work\4s\Requests Assembla Production\RequestsSource\RequestsSource\Requests\WEB-INF\config" /E /I /Y
xcopy "C:\Program Files\Apache Software Foundation\Tomcat 8.5\webapps\Requests\WEB-INF\tags" "D:\Work\4s\Requests Assembla Production\RequestsSource\RequestsSource\Requests\WEB-INF\tags" /E /I /Y
xcopy "C:\Program Files\Apache Software Foundation\Tomcat 8.5\webapps\Requests\WEB-INF\lib" "D:\Work\4s\Requests Assembla Production\RequestsSource\RequestsSource\Requests\WEB-INF\lib" /E /I /Y
xcopy "C:\Program Files\Apache Software Foundation\Tomcat 8.5\webapps\Requests\WEB-INF\classes\com" "D:\Work\4s\Requests Assembla Production\RequestsSource\RequestsSource\Requests\WEB-INF\classes\com" /E /I /Y
xcopy "C:\Program Files\Apache Software Foundation\Tomcat 8.5\webapps\Requests\WEB-INF\classes\com\*" "D:\Work\4s\Requests Assembla Production\RequestsSource\RequestsSource\Requests\WEB-INF\classes\com\*" /E /I /Y