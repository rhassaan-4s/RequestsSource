del /S H:\Requests Assembla Production\Requests\dbUpdates\* /q
del /S H:\Requests Assembla Production\Requests\web\* /q
del /S H:\Requests Assembla Production\Requests\WEB-INF\config\* /q
del /S H:\Requests Assembla Production\Requests\WEB-INF\tags\* /q
del /S H:\Requests Assembla Production\Requests\WEB-INF\lib\* /q
del /S H:\Requests Assembla Production\Requests\WEB-INF\classes\com\* /q

xcopy "C:\Program Files (x86)\Apache Software Foundation\Tomcat 5.5\webapps\Requests\dbUpdates" "H:\Requests Assembla Production\Requests\dbUpdates" /E /I /Y
xcopy "C:\Program Files (x86)\Apache Software Foundation\Tomcat 5.5\webapps\Requests\web" "H:\Requests Assembla Production\Requests\web" /E /I /Y
xcopy "C:\Program Files (x86)\Apache Software Foundation\Tomcat 5.5\webapps\Requests\WEB-INF" "H:\Requests Assembla Production\Requests\WEB-INF" /I /Y
xcopy "C:\Program Files (x86)\Apache Software Foundation\Tomcat 5.5\webapps\Requests\WEB-INF\config" "H:\Requests Assembla Production\Requests\WEB-INF\config" /E /I /Y
xcopy "C:\Program Files (x86)\Apache Software Foundation\Tomcat 5.5\webapps\Requests\WEB-INF\tags" "H:\Requests Assembla Production\Requests\WEB-INF\tags" /E /I /Y
xcopy "C:\Program Files (x86)\Apache Software Foundation\Tomcat 5.5\webapps\Requests\WEB-INF\lib" "H:\Requests Assembla Production\Requests\WEB-INF\lib" /E /I /Y
xcopy "C:\Program Files (x86)\Apache Software Foundation\Tomcat 5.5\webapps\Requests\WEB-INF\classes\com" "H:\Requests Assembla Production\Requests\WEB-INF\classes\com" /E /I /Y
xcopy "C:\Program Files (x86)\Apache Software Foundation\Tomcat 5.5\webapps\Requests\WEB-INF\classes\com\*" "H:\Requests Assembla Production\Requests\WEB-INF\classes\com\*" /E /I /Y