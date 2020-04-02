data <- read.csv("R_Vraag_2.csv")
data$primarytitle <- NULL
model =lm(director_count~rating, data =data)
plot(data$director_count, data$rating, xlab = "Directors in series", ylab = "Rating")
abline(model, col = "red")
cor(data$director_count, data$rating)
summary(model)

[1] -0.3848284

Call:
lm(formula = director_count ~ rating, data = data)

Residuals:
    Min      1Q  Median      3Q     Max 
-28.031 -10.739  -1.770   9.969  48.354 

Coefficients:
            Estimate Std. Error t value Pr(>|t|)    
(Intercept)  137.193     26.857   5.108 1.58e-06 ***
rating       -13.230      3.189  -4.148 7.08e-05 ***
---
Signif. codes:  0 ‘***’ 0.001 ‘**’ 0.01 ‘*’ 0.05 ‘.’ 0.1 ‘ ’ 1

Residual standard error: 15.95 on 99 degrees of freedom
Multiple R-squared:  0.1481,	Adjusted R-squared:  0.1395 
F-statistic: 17.21 on 1 and 99 DF,  p-value: 7.083e-05