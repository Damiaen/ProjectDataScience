data <- read.csv("R_Vraag_1.csv") 
data$title <- NULL 
plot(data$seasons, data$rating) 
model = lm(seasons~rating ,data = data) 
abline(model, col = "red")
cor(data$seasons, data$rating)

## [1] -0.2426117

summary(model)

## 
## Call: 
## lm(formula = seasons ~ rating, data = data)
## 
## Residuals: ## Min 1Q Median 3Q Max ## -5.135 -3.049 -1.016 1.201 25.760 
## 
## Coefficients: ## Estimate Std. Error t value Pr(>|t|) 
## (Intercept) 25.7040 7.6621 3.355 0.00113 ** 
## rating -2.2373 0.9037 -2.476 0.01501 * 
## --- 
## Signif. codes: 0 '***' 0.001 '**' 0.01 '*' 0.05 '.' 0.1 ' ' 1 
## 
## Residual standard error: 4.658 on 98 degrees of freedom 
## Multiple R-squared: 0.05886, Adjusted R-squared: 0.04926 
## F-statistic: 6.129 on 1 and 98 DF, p-value: 0.01501