library(gdata)  

setwd("/Users/arunkaly/Box Documents/Spring_2014/computational_modeling_of_complex_socio_technical/Project/my_proj/Paper/Scripts/data")
getwd()
mydata1 = read.csv("Alpha_0.05_Turns.csv") 
mydata2 = read.csv("Alpha_0.05_Efficiency.csv") 
mydata_means1 = colMeans(mydata1)
mydata_means2 = colMeans(mydata2)
mydata_means1

dev.off()
split.screen(c(1,2))
#split.screen(c(1,2), screen=1)
screen(1)
boxplot(mydata1,las=1, col=c("wheat2"), ylab="# Turns to reach consensus", xlab="Mean agent credibility  (a)", 
        main="Number of Turns to reach consensus vs Agent credibility",
        names=c("0.2","0.4***", "0.6*","0.8***"), outline = FALSE)
points(mydata_means1, pch = 17, col = "gray30", lwd = 2)
screen(2)
boxplot(mydata2,las=1, col=c("wheat2"), ylab= "Efficiency", xlab="Mean agent credibility  (b)", 
       main="Efficiency vs Agent credibility",
       names=c("0.2","0.4***", "0.6***","0.8***"), outline = FALSE)
points(mydata_means2, pch = 17, col = "gray35", lwd = 2)

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

