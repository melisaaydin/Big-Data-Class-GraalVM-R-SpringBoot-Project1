library(lattice)

global_x <- c()
global_y <- c()

plotFunction <- function(dataHolder) {
  global_x <<- c(global_x, length(global_x))
  global_y <<- c(global_y, dataHolder$value)

  svg()

  print(xyplot(global_y ~ global_x, type = "l", col = "saddlebrown", grid = TRUE,
               xlab = "Index (0-99)", ylab = "Value"))

  svg.off()
}