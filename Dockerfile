FROM maven:3.8-openjdk-17-slim
#FROM eclipse-temurin:17-jdk-jammy

ENV HOME=/home/laurent
RUN mkdir -p $HOME

ADD ./settings.xml /usr/share/maven/ref/
ENV MVN_SETTINGS=/usr/share/maven/ref/settings.xml

RUN mkdir -p $HOME/TelegramBotLaurentTransport
ADD ./ $HOME/TelegramBotLaurentTransport
ADD ./src $HOME/TelegramBotLaurentTransport/src
RUN mvn -f $HOME/TelegramBotLaurentTransport/pom.xml -s $MVN_SETTINGS clean package -Dskiptests --debug

CMD java -jar $HOME/TelegramBotLaurentTransport/target/TelegramBotLaurentTransport-0.0.1.jar