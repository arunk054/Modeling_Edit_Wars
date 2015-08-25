library(gdata)  

setwd("/Users/arunkaly/Box Sync/Spring_2014/computational_modeling_of_complex_socio_technical/Project/my_proj/Paper/Scripts/data")
getwd()
mydata1 = read.csv("Alpha_0.02_Continuous.csv") 

dev.off()
split.screen(c(1,2))
#split.screen(c(1,2), screen=1)

x <- mydata1[,1]
y <- mydata1[,2]
screen(1)
p1=plot(y~x,ylab="# Turns to reach consensus", xlab="Mean agent credibility  (a)",
        cex.lab=1.5,cex.axis=1.5, cex.main=1.3, main="Number of Turns to reach consensus vs Agent credibility",
        ylim=c(15,60))
lines(loess.smooth(x,y), lwd=2)

x <- mydata1[,1]
y <- mydata1[,3]
screen(2)
p2=plot(y~x,ylab= "Productivity", xlab="Mean agent credibility  (b)",
        cex.lab=1.5,cex.axis=1.5, cex.main=1.3, main="Productivity vs Agent credibility",
        ylim=c(.5,.8))
lines(loess.smooth(x,y), lwd=2)

dev.off()
summary(mydata1)
library(psych)
describe(mydata1)
stMyData = stack(mydata1)

names(stMyData) = c("Turns","Condition")

av1 = aov(Turns~Condition,data=stMyData)
summary(av1)
tk = TukeyHSD(av1)
tk
plot(tk)


summary(mydata2)
library(psych)
describe(mydata2)
stMyData = stack(mydata2)

names(stMyData) = c("Turns","Condition")

av1 = aov(Turns~Condition,data=stMyData)
summary(av1)
tk = TukeyHSD(av1)
tk
plot(tk)

