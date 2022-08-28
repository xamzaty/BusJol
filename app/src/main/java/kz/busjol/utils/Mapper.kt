package kz.busjol.utils


abstract class Mapper<in FROM, out TO> {
    abstract fun map(from: FROM): TO

    fun mapList(input: List<FROM>): List<TO> {
        return input.map{ map(it) }
    }
}