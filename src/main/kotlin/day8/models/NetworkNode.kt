package day8.models

class NetworkNode(val id: String) {
    lateinit var left: NetworkNode
        private set
    lateinit var right: NetworkNode
        private set


    fun setNeighbors(left: NetworkNode, right: NetworkNode){
        this.left = left
        this.right = right
    }

    override fun toString(): String {
        return id
    }
}