# AoC2015Day13

## Structure of the solution
* The solution is primarily carried out in the class Day13, this is the main focus.
* The Data class is a utility class for holding the raw input data.
* The Edge class is a utility class to simplify handling seating relations between people.
* The TreeNode class is used to construct the tree structure (depth/level, direct children, relations to direct children).

## Assumptions and caveats
* No deviations in input formatting.
* No input validation or exception/error handling.
* All relations are complete, i.e. no missing seating preferences.
* part1() is executed before part2().
* Brute-force approach, iterates over all possible combinations. Efficiency is not optimal (the tree is reconstructed for part2(), evaluated seating combinations could possibly be reduced).
