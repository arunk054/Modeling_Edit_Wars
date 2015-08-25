library(gdata)  
library(ggplot2)
setwd("/Users/arunkaly/Box Sync/Spring_2014/computational_modeling_of_complex_socio_technical/Project/my_proj/Paper/Scripts/data")
getwd()
mydata1 = read.csv("PCommit_0.05_Turns.csv") 
mydata2 = read.csv("PCommit_0.05_Productivity.csv") 
mydata1
mydata_means1 = colMeans(mydata1)
mydata_means2 = colMeans(mydata2)
mydata_means1

dev.off()
split.screen(c(1,2))
screen(1)
p1= boxplot(mydata1,las=1, col=c("wheat2"), ylab="# Turns to reach consensus", xlab="Mean likelihood to commit  (a)", 
        main="Number of Turns to reach consensus vs Likelihood to commit",
        names=c("0.2","0.4***", "0.6","0.8"), cex.lab=1.2,cex.axis=1.3, cex.main=1.2, outline = FALSE)
points(mydata_means1, pch = 17, col = "gray30", lwd = 2)

screen(2)
p2 = boxplot(mydata2,las=1, col=c("wheat2"), ylab= "Productivity", xlab="Mean likelihood to commit  (b)", 
       main="Productivity vs Likelihood to commit",
        names=c("0.2","0.4***", "0.6***","0.8***"), cex.lab=1.2,cex.axis=1.3, cex.main=1.2,outline = FALSE)
points(mydata_means2, pch = 17, col = "gray30", lwd = 2)


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

