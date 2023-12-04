package day4.models

data class Scratchcard(val id: Int, val winningNumbers: List<Int>, val numbers: List<Int>) {
    val points: Int;
    var matches = 0
        private set
    init {
        var points = 0
        for (number in numbers){
            for (winningNumber in winningNumbers){
                if(winningNumber == number){
                    points = if(points == 0) 1 else points * 2
                    matches++
                }
            }
        }
        this.points = points
    }
}