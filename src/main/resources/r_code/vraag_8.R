data <- read.csv("R_Vraag_3.csv") 
data$title <- NULL 
plot(data$seasons, data$rating) 
model = lm(rating~seasons ,data = data) 
abline(model, col = "red")
cor(data$seasons, data$rating)

## [1] 0.4325725

summary(model)

## 
## Call: 
## lm(formula = rating ~ seasons, data = data) 
## 
## Residuals: 
## Min 1Q Median 3Q Max 
## -0.95288 -0.09553 0.02093 0.27771 0.66418 
## 
## Coefficients: 
## Estimate Std. Error t value Pr(>|t|) 
## (Intercept) 7.490512 0.469084 15.968 2.37e-07 *** 
## seasons 0.001765 0.001300 1.357 0.212 
## --- 
## Signif. codes: 0 '***' 0.001 '**' 0.01 '*' 0.05 '.' 0.1 ' ' 1 
## 
## Residual standard error: 0.4988 on 8 degrees of freedom 
## Multiple R-squared: 0.1871, Adjusted R-squared: 0.08551 
## F-statistic: 1.842 on 1 and 8 DF, p-value: 0.2118