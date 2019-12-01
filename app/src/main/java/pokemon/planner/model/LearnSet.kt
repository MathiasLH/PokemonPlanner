package pokemon.planner.model

import java.io.Serializable

class LearnSet(
    var moves: ArrayList<Move>,
    var moveSources: ArrayList<Int>,
    var moveCriteria: ArrayList<Int>

):Serializable{
    fun addMove(move: Move, source: Int, criteria: Int){
        moves.add(move)
        moveSources.add(source)
        moveCriteria.add(criteria)
    }

    fun isLearnable(move: Move):Boolean{
        for(i in 0..moves.size-1){
            if(moves[i].id == move.id){
                return true
            }
        }
        return false
    }

}