library(gdata)  

setwd("/Users/arunkaly/Box Sync/Spring_2014/computational_modeling_of_complex_socio_technical/Project/my_proj/Paper/Scripts/data")
getwd()
mydata1 = read.csv("Groups_Turns.csv") 
mydata2 = read.csv("Groups_Productivity.csv") 

mydata1
mydata1_means = colMeans(mydata1)
mydata1_means
mydata2_means = colMeans(mydata2)
dev.off() 
split.screen(c(2,1))
#loc = c(1,3,5,7,9,11)

screen(1)
boxplot(mydata1,range=0.001,  las=1, col=c("wheat2"), ylab="# Turns to reach consensus", xlab="(a)", yaxt="n",
        main="Number of Turns to reach consensus vs Group composition",
        names=c("Baseline","Opp. sides**", "Pos. Dominant**","Neg. Dominant**","50% M**","75% M**","90% M**"), cex.lab=1.2,cex.axis=1.26, cex.main=1.2, outline = FALSE)
points(mydata1_means, pch = 17, col = "gray35", lwd = 2)
axis(side=2,at=seq(0,70,by=20),las=2)

screen(2)
boxplot(mydata2,las=1 ,range=0.001, col=c("wheat2"), ylab= "Productivity", xlab="(b)", yaxt="n",
        main="Productivity vs Group Composition",
        names=c("Baseline","Opp. sides**", "Pos. Dominant**","Neg. Dominant**","50% M**","75% M**","90% M**"), cex.lab=1.2,cex.axis=1.26, cex.main=1.2, outline = FALSE)
points(mydata2_means, pch = 17, col = "gray35", lwd = 2)
axis(side=2,at=seq(0.5,1,by=0.1),las=2)

summary(mydata1)
library(psych)
describe(mydata1)
stMyData = stack(mydata1)
stMyData
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
stMyData
names(stMyData) = c("Productivity","Condition")

av1 = aov(Turns~Condition,data=stMyData)
summary(av1)
tk = TukeyHSD(av1)
tk
plot(tk)