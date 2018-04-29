### ambari 安装
#### apt源安装
wget -O /etc/apt/sources.list.d/ambari.list http://public-repo-1.hortonworks.com/ambari/ubuntu16/2.x/updates/2.6.1.5/ambari.list
apt-key adv --recv-keys --keyserver keyserver.ubuntu.com B9733A7A07513CAD
apt-get update
apt-get install ambari-server
ambari-server setup
ambari-server start
#### 源码安装
1. 修改ambari-metrics的pom文件，将hbase.tar等文件下载到本地并配置file://路径
2. mvn -B  clean package install jdeb:jdeb -DnewVersion=2.6.1.0.0 -DskipTests -Dphon.ver="python >= 2.6" -Preplaceurl -DsocksProxyHost=127.0.0.1 -DsocksProxyPort=1080
3. dpkg -i ambari-server/target/ambari-server_2.6.1.0-0-dist.deb
- FAQ
1. org.vafer相关的导致某些模块下的control　is not a valid 'control' directory，修改pom文件
```
<plugin>

       <groupId>org.vafer</groupId>

       <artifactId>jdeb</artifactId>

       <version>1.0.1</version>

       <executions>

         <execution>

           <!--Stub execution on direct plugin call - workaround for ambari debbuild process-->

           <id>stub-execution</id>

           <phase>none</phase>

           <goals>

              <goal>jdeb</goal>

            </goals>

         </execution>

       </executions>

       <configuration>

         <skip>true</skip>

         <attach>false</attach>

         <submodules>false</submodules>

         <controlDir>${project.basedir}/../src/main/package/deb/control</controlDir>

       </configuration>

     </plugin>

```
2. storm版本
```
org.apache.storm:storm-core:jar:1.1.0-SNAPSHOT修改伟org.apache.storm:storm-core:jar:1.1.0
```
3. 配置mysql
```
https://docs.hortonworks.com/HDPDocuments/Ambari-2.6.1.5/bk_ambari-administration/content/using_ambari_with_mysql.html
CREATE USER 'ambari'@'%' IDENTIFIED BY 'ambari';
GRANT ALL PRIVILEGES ON *.* TO 'ambari'@'%';
CREATE USER 'ambari'@'localhost' IDENTIFIED BY 'ambari';
GRANT ALL PRIVILEGES ON *.* TO 'ambari'@'localhost';
grant all privileges on *.* to 'ambari'@'localhost' identified by 'ambari';
CREATE USER 'ambari'@'ljw-ThinkPad-Edge-E440' IDENTIFIED BY 'ambari';
GRANT ALL PRIVILEGES ON *.* TO 'ambari'@'ljw-ThinkPad-Edge-E440';
FLUSH PRIVILEGES;
```
4. 免密
```
ssh-keygen -t rsa
cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys 
```