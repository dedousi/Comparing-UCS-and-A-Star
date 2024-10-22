# This is a problem that uses UCS and A* algorithms to get solved.
Problem Statement: 
<br>
You have a list of N positions filled with the numbers 1 to N (each appearing once). The goal is to sort them in ascending order, starting from an initial state provided by the user. The final state is [1, 2, ..., N].
<br>
Transition Operators: Given a state a=[a1,...,aN]a=[a1​,...,aN​], applying the operator T(k)T(k) (for 2≤k≤N2≤k≤N) results in a new state bb:
<br>
    Split aa into aL=[a1,...,ak]aL​=[a1​,...,ak​] and aR=[ak+1,...,aN]aR​=[ak+1​,...,aN​].
    Reverse aLaL​ to get raLraL​.
    The new state b=raL+aRb=raL​+aR​.
<br>
Example:
<br>
    For a=[3,5,4,1,2]a=[3,5,4,1,2]:
        T(2)T(2) gives [5,3,4,1,2][5,3,4,1,2]
        T(4)T(4) gives [1,4,5,3,2][1,4,5,3,2]
        T(5)T(5) gives [2,1,4,5,3][2,1,4,5,3]
<br>
The program should output: a) The path taken, b) The cost of the path, c) The number of expansions made.
