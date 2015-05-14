library(gdata)  


getwd()
mydata = read.csv("Groups_Turns.csv") 
mydata = read.csv("Groups_Efficiency.csv") 
mydata
mydata_means = colMeans(mydata)
mydata_means
dev.off()
boxplot(mydata,las=1, col=c("wheat2"), ylab="# Turns to reach consensus", xlab="<= Group composition =>", 
        main="Number of Turns to reach consensus vs Group composition",
        names=c("Baseline","Two opposing sides***", "Posivite Dominant***","Negative Dominant***","50% Moderates***", "All Moderates***"), outline = FALSE)
#abline(a=2, b=0, h = mydata_means, untf=FALSE)
points(mydata_means, pch = 17, col = "gray35", lwd = 2)

#boxplot(mydata,las=1, col=c("wheat2"), ylab= "Efficiency", xlab="<= Group composition =>", 
 #       main="Efficiency vs Group Composition",
  #      names=c("Baseline","Two opposing sides***", "Posivite Dominant***","Negative Dominant***","50% Moderates***","All Moderates***"), outline = FALSE)
#points(mydata_means, pch = 17, col = "gray35", lwd = 2)

summary(mydata)
library(psych)
describe(mydata)
stMyData = stack(mydata)
stMyData
names(stMyData) = c("Turns","Condition")

av1 = aov(Turns~Condition,data=stMyData)
summary(av1)
tk = TukeyHSD(av1)
tk
plot(tk)
