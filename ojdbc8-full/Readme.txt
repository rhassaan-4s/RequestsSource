======================================================
Oracle Free Use Terms and Conditions (FUTC) License 
======================================================
https://www.oracle.com/downloads/licenses/oracle-free-license.html
===================================================================

ojdbc8-full.tar.gz - JDBC Thin Driver and Companion JARS
========================================================
This TAR archive (ojdbc8-full.tar.gz) contains the 19.32.0.0 release of the Oracle JDBC Thin driver(ojdbc8.jar), the Universal Connection Pool (ucp.jar) and other companion JARs grouped by category. 

(1) ojdbc8.jar (4548735 bytes) - 
(SHA1 Checksum: 73f9f800250e7e69ea05084b4aea622e75f7426a)
Oracle JDBC Driver compatible with JDK8, JDK9, and JDK11;
(2) ucp.jar (1705939 bytes) - (SHA1 Checksum: 4c88e17121cdde5c5d99da5a44b85097b0cf7853)
Universal Connection Pool classes for use with JDK8, JDK9, and JDK11 -- for performance, scalability, high availability, sharded and multitenant databases.
(3) ojdbc.policy (12134 bytes) - Sample security policy file for Oracle Database JDBC drivers

======================
Security Related JARs
======================
Java applications require some additional jars to use Oracle Wallets. 
You need to use all the three jars while using Oracle Wallets. 

(4) oraclepki.jar (562145 bytes ) - (SHA1 Checksum: 167cca50456468cdede2022e70ed610c6a5f1987
Additional jar required to access Oracle Wallets from Java
(5) osdt_cert.jar (210642 bytes) - (SHA1 Checksum: ad2d0b4d35e87e61037e895ef15400430ebebb3b)
Additional jar required to access Oracle Wallets from Java
(6) osdt_core.jar (313846 bytes) - (SHA1 Checksum: 627a0b44b257110c9555cab25f814df3e7d73239)
Additional jar required to access Oracle Wallets from Java

=============================
JARs for NLS and XDK support 
=============================
(7) orai18n.jar (1668626 bytes) - (SHA1 Checksum: a0185425d12b66fcf3dc72ebb13e73c5e0128bfd) 
Classes for NLS support
(8) xdb.jar (132015 bytes) - (SHA1 Checksum: 015acf1fae3f98f7b07701a25ad1d22651bc9a48)
Classes to support standard JDBC 4.x java.sql.SQLXML interface 
(9) xmlparserv2.jar (1933033 bytes) - (SHA1 Checksum: 0dfc02cbf219b4d3cb60bf7c04d3012345793b50)
Classes to support standard JDBC 4.x java.sql.SQLXML interface 
(10) xmlparserv2_sans_jaxp_services.jar (1931939 bytes) - (SHA1 Checksum: 280318a99b541ef8e527be5a26b08a6c78e024ad) 
Classes to support standard JDBC 4.x java.sql.SQLXML interface

====================================================
JARs for Real Application Clusters(RAC), ADG, or DG 
====================================================
(11) ons.jar (157054 bytes ) - (SHA1 Checksum: a30a916ffed95ef0fe28a4393590f556e9ce411a)
for use by the pure Java client-side Oracle Notification Services (ONS) daemon
(12) simplefan.jar (32397 bytes) - (SHA1 Checksum: 7701cfda77267823c8bbf127ce8b9e50107838b5)
Java APIs for subscribing to RAC events via ONS; simplefan policy and javadoc

==================================================================================
NOTE: The diagnosability JARs **SHOULD NOT** be used in the production environment. 
These JARs (ojdbc8_g.jar,ojdbc8dms.jar, ojdbc8dms_g.jar) are meant to be used in the 
development, testing, or pre-production environment to diagnose any JDBC related issues. 

=====================================
OJDBC - Diagnosability Related JARs
===================================== 

(13) ojdbc8_g.jar (7619830 bytes) - (SHA1 Checksum: b0068b1f5fe92d012a11c4bf5ce109849fac9e44)
Same as ojdbc8.jar except compiled with "javac -g" and contains tracing code.

(14) ojdbc8dms.jar (6334321 bytes) - (SHA1 Checksum: 176f6a4227854afd74f3c31f9aba450d6f266958)
Same as ojdbc8.jar, except that it contains instrumentation to support DMS and limited java.util.logging calls.

(15) ojdbc8dms_g.jar (7649699 bytes) - (SHA1 Checksum: d38c751aacbacf8d7c41ff2c1c5b76a80a8a1b0e)
Same as ojdbc8_g.jar except that it contains instrumentation to support DMS.

(16) dms.jar (2194533 bytes) - (SHA1 Checksum: cb20f6da4888d906ae44013dbec2cec0880d9941)
dms.jar required for DMS-enabled JAR files.

==================================================================
Oracle JDBC and UCP - Javadoc and README
==================================================================

(17) JDBC-Javadoc-19c.jar (1766076 bytes) - JDBC API Reference 19c

(18) ucp-Javadoc-19c.jar (367142 bytes) - UCP Java API Reference 19c

(19) simplefan-Javadoc-19c.jar (84188 bytes) - Simplefan API Reference 19c 

(20) xdb-Javadoc-19c.jar (2861664 bytes) - XDB API Reference 19c 

(21) xmlparserv2-Javadoc-19c.jar (2861664 bytes) - xmlparserv2 API Reference 19c 

(22) Jdbc-Readme.txt: It contains general information about the JDBC driver and bugs that have been fixed in the 19.32.0.0 release. 

(23) UCP-Readme.txt: It contains general information about UCP and bugs that are fixed in the 19.32.0.0 release. 


=================
USAGE GUIDELINES
=================
Refer to the JDBC Developers Guide (https://docs.oracle.com/en/database/oracle/oracle-database/19/jjdbc/index.html) and Universal Connection Pool Developers Guide (https://docs.oracle.com/en/database/oracle/oracle-database/19/jjucp/index.html) for more details. 
