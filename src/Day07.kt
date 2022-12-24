import java.util.ArrayDeque

sealed class Entity {
    class File(val name: String, val size: Int): Entity()
    class Folder(val name: String, val parent: Folder?): Entity() {
        val children = mutableMapOf<String, Entity>()
        var size = 0
    }
}

fun main() {
    fun buildFileSystem(terminal: List<String>): Entity.Folder {
        fun fixFolderSizes(folder: Entity.Folder): Int {
            var size = 0

            for (entity in folder.children.values) {
                if (entity is Entity.File) {
                    size += entity.size
                } else {
                    size += fixFolderSizes(entity as Entity.Folder)
                }
            }

            folder.size = size
            return size
        }

        val root = Entity.Folder("/", null)

        var currentFolder = root
        for (line in terminal) {
            if (line == "$ cd /") {
                currentFolder = root
            } else if (line == "$ cd ..") {
                currentFolder = currentFolder.parent!!
            } else if (line.startsWith("$ cd")) {
                val name = line.split(" ").last()
                currentFolder = currentFolder.children[name] as Entity.Folder
            } else if (line == "$ ls") {
                // Do nothing.
            } else if (line.startsWith("dir")) {
                val dirName = line.split(" ").last()
                val folder = Entity.Folder(dirName, currentFolder)
                currentFolder.children[dirName] = folder
            } else {
                val (size, fileName) = line.split(" ")
                val file = Entity.File(fileName, size.toInt())
                currentFolder.children[fileName] = file
            }
        }

        fixFolderSizes(root)

        return root
    }

    fun visitAllFolders(folder: Entity.Folder, visitor: (folder: Entity.Folder) -> Unit) {
        visitor(folder)

        for (entity in folder.children.values) {
            if (entity is Entity.Folder) {
                visitAllFolders(entity, visitor)
            }
        }
    }

    fun part1(input: List<String>): Int {
        val root = buildFileSystem(input)
        var result = 0

        visitAllFolders(root) { folder -> if (folder.size <= 100000) result += folder.size }

        return result
    }

    fun part2(input: List<String>): Int {
        val root = buildFileSystem(input)
        val needToFree = 30000000 - (70000000 - root.size)
        var result = Int.MAX_VALUE

        visitAllFolders(root) { folder -> if (folder.size >= needToFree) result = Math.min(result, folder.size) }

        return result
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 95437)
    check(part2(testInput) == 24933642)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}