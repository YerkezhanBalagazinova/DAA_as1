# Divide-and-Conquer Algorithm Analysis

## A. Project Overview

### Purpose

The purpose of this assignment is to learn how divide-and-conquer algorithms work and compare their theoretical complexity with their actual performance in Java.

In this project, I implemented the algorithms myself, tested their correctness, and then ran experiments with different input sizes and input types. During the experiments, I measured execution time, maximum recursion depth, and the number of comparisons.

### Implemented Algorithms

The project includes four algorithms:

- Merge Sort
- Randomized QuickSort
- Deterministic Select (Median-of-Medians)
- Closest Pair of Points

---

## B. Algorithm Analysis

### 1. Merge Sort

#### How it works

Merge Sort divides an array into two halves. Each half is sorted recursively, and then the two sorted halves are merged back together.

In my implementation, I create one auxiliary buffer and reuse it during merging instead of creating a new buffer every time.

I also use a cutoff of 4. If the current part contains 4 or fewer elements, I use Insertion Sort instead of dividing it again.

#### Time and Space Complexity

The time complexity of Merge Sort is:

`Θ(n log n)`

Its additional space complexity is:

`Θ(n)`

because the algorithm uses an auxiliary buffer.

#### Recurrence

The main recurrence is:

`T(n)=2T(n/2)+Θ(n)`

There are two recursive calls, each working on approximately half of the array. Merging the two halves takes linear time.

Using the Master Theorem:

- `a=2`
- `b=2`
- `f(n)=Θ(n)`
- `n^(log_b(a))=n`

Here `f(n)` has the same order as `n^(log_b(a))`, so this is Case 2 of the Master Theorem.

Therefore:

`T(n)=Θ(n log n)`

---

### 2. Randomized QuickSort

#### How it works

QuickSort chooses a pivot and partitions the array around it.

In my implementation, the pivot is selected randomly. Values smaller than or equal to the pivot are moved to one side, and the other values remain on the other side.

I also used smaller-first recursion. After partitioning, the algorithm recursively processes the smaller partition and uses a loop for the larger partition. This helps reduce recursion depth.

#### Time and Space Complexity

For balanced partitions, QuickSort has expected running time:

`O(n log n)`

Its worst-case running time is:

`O(n²)`

The algorithm sorts the array in place. With smaller-first recursion, the recursion stack is typically `O(log n)`.

#### Recurrence

For approximately balanced partitions:

`T(n)=2T(n/2)+Θ(n)`

Using the Master Theorem:

- `a=2`
- `b=2`
- `f(n)=Θ(n)`

This gives:

`T(n)=Θ(n log n)`

However, in a very unbalanced case:

`T(n)=T(n-1)+Θ(n)`

This gives the worst-case complexity:

`O(n²)`

Random pivot selection reduces the chance of repeatedly getting bad partitions, but the worst case is still possible.

---

### 3. Deterministic Select (Median-of-Medians)

#### How it works

Deterministic Select finds the k-th smallest element without sorting the complete array.

The algorithm divides the current part of the array into groups of 5. Each small group is sorted, and its median is found.

The medians are then used to find a median-of-medians pivot. After partitioning around this pivot, the algorithm continues only in the part that contains the required k-th position.

Unlike sorting algorithms, it does not need to process both sides.

#### Time and Space Complexity

The worst-case time complexity is:

`Θ(n)`

The implementation mainly rearranges values inside the original array, although recursion still requires stack space.

#### Recurrence

The recurrence can be written as:

`T(n)≤T(n/5)+T(7n/10)+Θ(n)`

`T(n/5)` is needed to find the median of the group medians.

The median-of-medians pivot also guarantees that the next selection step works on only a limited fraction of the original input.

Using Akra-Bazzi intuition, the recursive parts shrink enough that the linear work done at each stage dominates the total running time.

Therefore:

`T(n)=Θ(n)`

---

### 4. Closest Pair of Points

#### How it works

The Closest Pair algorithm finds the minimum distance between any two points.

First, the points are sorted by their x-coordinate and y-coordinate.

The algorithm divides the points into a left half and a right half. It recursively finds the closest distance in both halves and keeps the smaller result.

However, the actual closest pair may contain one point from each half. Because of this, the algorithm creates a strip around the dividing line and checks possible closer pairs inside this strip in y-order.

For very small groups of points, my implementation uses brute force.

#### Time and Space Complexity

The divide-and-conquer version has a time complexity of:

`Θ(n log n)`

This is better than the simple brute-force solution:

`Θ(n²)`

for large inputs.

#### Recurrence

The main recurrence is:

`T(n)=2T(n/2)+Θ(n)`

The two recursive calls solve the left and right halves. The additional work for dividing and checking the strip is linear.

Using the Master Theorem:

- `a=2`
- `b=2`
- `f(n)=Θ(n)`

Therefore:

`T(n)=Θ(n log n)`

---

## C. Experimental Results

### Experimental Setup

I tested three input sizes:

| Size | Number of elements |
|---|---:|
| Small | 1,000 |
| Medium | 10,000 |
| Large | 100,000 |

The experiments measured:

- execution time in nanoseconds
- maximum recursion depth
- number of comparisons

The array algorithms were tested with Random, Sorted, Reverse, and Duplicate-heavy inputs.

Each experiment was repeated 5 times, and the median result was used to reduce timing noise.

### Execution Time Results

#### Random Input

| Algorithm | n=1,000 | n=10,000 | n=100,000 |
|---|---:|---:|---:|
| Merge Sort | 99,200 ns | 1,070,800 ns | 8,856,100 ns |
| QuickSort | 57,000 ns | 570,700 ns | 6,614,300 ns |
| Deterministic Select | 130,400 ns | 207,900 ns | 2,020,500 ns |
| Closest Pair | 1,190,200 ns | 14,433,100 ns | 92,540,000 ns |

#### Sorted Input

| Algorithm | n=1,000 | n=10,000 | n=100,000 |
|---|---:|---:|---:|
| Merge Sort | 34,700 ns | 155,300 ns | 1,578,000 ns |
| QuickSort | 33,300 ns | 230,500 ns | 2,528,100 ns |
| Deterministic Select | 197,700 ns | 188,100 ns | 3,601,700 ns |

#### Reverse-Sorted Input

| Algorithm | n=1,000 | n=10,000 | n=100,000 |
|---|---:|---:|---:|
| Merge Sort | 46,100 ns | 201,900 ns | 1,970,400 ns |
| QuickSort | 38,300 ns | 270,700 ns | 2,923,600 ns |
| Deterministic Select | 167,400 ns | 85,800 ns | 931,600 ns |

#### Duplicate-Heavy Input

| Algorithm | n=1,000 | n=10,000 | n=100,000 |
|---|---:|---:|---:|
| Merge Sort | 63,200 ns | 334,000 ns | 3,840,100 ns |
| QuickSort | 69,000 ns | 4,301,800 ns | 389,931,400 ns |
| Deterministic Select | 61,600 ns | 173,800 ns | 1,691,300 ns |

The biggest difference appeared in QuickSort with duplicate-heavy data. At `n=100,000`, its execution time increased to `389,931,400 ns`, which was much higher than its time on random input.

### Recursion Depth Results

#### Random Input

| Algorithm | n=1,000 | n=10,000 | n=100,000 |
|---|---:|---:|---:|
| Merge Sort | 9 | 13 | 16 |
| QuickSort | 7 | 9 | 12 |
| Deterministic Select | 10 | 14 | 17 |
| Closest Pair | 10 | 13 | 17 |

The recursion depth increased slowly compared with the input size.

For example, Merge Sort increased from depth 9 at `n=1,000` to only 16 at `n=100,000`.

QuickSort also kept a relatively small recursion depth because the smaller partition was processed recursively while the larger partition was handled iteratively.

### Results for Different Input Types

The experiments showed that input structure can affect the algorithms differently.

Merge Sort remained relatively stable because it always divides the input into two halves.

Randomized QuickSort worked well on random, sorted, and reverse-sorted inputs. However, duplicate-heavy input caused a very large increase in comparisons and execution time.

Deterministic Select also changed depending on the input, but it did not show the same extreme slowdown on duplicate-heavy data.

The complete experimental results, including comparison counts, are stored in `results.csv`.

### Time vs. n

![Time vs n](docs/plots/time_vs_n.png)

The graph shows how execution time changes when the input increases from 1,000 to 100,000.

### Recursion Depth vs. n

![Recursion Depth vs n](docs/plots/recursion_depth_vs_n.png)

The recursion-depth graph shows that recursion depth grows much more slowly than the input size for the divide-and-conquer algorithms.

---

## D. Discussion

### Do the results match theoretical complexity?

Overall, the experimental results mostly follow the theoretical expectations.

Merge Sort showed predictable growth as the input became larger, which agrees with its `Θ(n log n)` complexity.

QuickSort performed well for most input types, but its performance was much worse for duplicate-heavy arrays. This also agrees with the fact that QuickSort can have `O(n²)` worst-case behavior.

Deterministic Select generally required fewer comparisons than sorting the entire array because it only searches for one required element.

The Closest Pair execution time increased with input size, but the divide-and-conquer approach avoids the `Θ(n²)` work of comparing every possible pair.

### How does input structure affect performance?

Input structure had the strongest effect on QuickSort.

For `n=100,000`, QuickSort made about 2 million comparisons on random input, but more than 500 million comparisons on duplicate-heavy input.

My partition implementation uses values `<= pivot` on one side. When many values are equal, this can create very unbalanced partitions and cause much more work.

Merge Sort was less sensitive to input structure because it divides the array into halves regardless of the values.

### Why does smaller-first recursion help QuickSort?

After partitioning, my QuickSort recursively processes only the smaller partition.

The larger partition is processed using the same `while` loop.

This prevents the recursion stack from growing unnecessarily when one partition is much larger than the other. The recursive side is always the smaller one, so the recursion depth stays relatively small.

### Why does Median-of-Medians guarantee O(n)?

Median-of-Medians chooses the pivot using groups of 5 instead of choosing an arbitrary value.

The medians of these groups are used to find a pivot that guarantees that a useful fraction of the elements can be removed from the next search.

Because the algorithm only continues into the partition containing the required element, the recurrence is:

`T(n)≤T(n/5)+T(7n/10)+Θ(n)`

This results in linear worst-case time:

`Θ(n)`

### Why is divide-and-conquer Closest Pair faster than O(n²) for large inputs?

The brute-force solution compares every pair of points.

As `n` grows, the number of possible pairs grows approximately with `n²`.

The divide-and-conquer algorithm avoids most of these comparisons. It recursively solves two smaller halves and only checks additional points inside a narrow strip near the dividing line.

Because of this, its complexity is `Θ(n log n)` instead of `Θ(n²)`.

### What practical factors affect performance?

Actual execution time can be affected by factors other than theoretical complexity.

Some examples are:

- JVM warm-up
- garbage collection
- CPU load
- memory access
- cache behavior
- random pivot choices in QuickSort
- other programs running at the same time

Because of these factors, two runs of the same algorithm may have slightly different execution times. For this reason, I repeated the experiments and used median results instead of relying on a single measurement.

---

## E. Reflection

This assignment was difficult for me because it was my first time implementing several of these divide-and-conquer algorithms in detail. At first, recursion was especially confusing because I had difficulty understanding what happened after a recursive call returned. Following small arrays step by step helped me understand Merge Sort and QuickSort much better. The most difficult algorithm for me was Deterministic Select because I had to understand groups of 5, medians, the median-of-medians pivot, and partitioning at the same time.

The experiments also helped me understand why theoretical complexity is important. Before doing the experiments, `O(n log n)` and `O(n²)` felt mostly like formulas. Seeing QuickSort become much slower on duplicate-heavy input made the difference more practical for me. I also learned how to measure execution time, recursion depth, and comparisons, how to test an algorithm against a reference solution, and why running an experiment several times gives more reliable results.
