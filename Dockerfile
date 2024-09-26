FROM maven:3.8-openjdk-17-slim

ENV HOME=/home/app
RUN mkdir -p $HOME

ADD ./settings.xml /usr/share/maven/ref/
ENV MVN_SETTINGS=/usr/share/maven/ref/settings.xml

RUN mkdir -p $HOME/telegrambotlaurenttransport
ADD ./telegrambotlaurenttransport/pom.xml $HOME/telegrambotlaurenttransport
ADD ./telegrambotlaurenttransport/src $HOME/telegrambotlaurenttransport/src
RUN mvn -f $HOME/telegrambotlaurenttransport/pom.xml -s $MVN_SETTINGS clean package -Dskiptests --debug

CMD java -jar $HOME/telegrambotlaurenttransport/target/telegrambotlaurenttransport-0.0.1.jar