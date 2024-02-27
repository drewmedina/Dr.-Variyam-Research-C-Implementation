#define hashsize (1 << 25)
#define maxsize 1000000
#define signbit 0x80000000
#define sanity_checking 0

#include <stdio.h>
#include <stdlib.h>
#include "gb_flip.h"
#include <string.h>
#include <ctype.h>

int bufsize, del, length, param, seed;
int ssize;

typedef struct node_struct
{
    int elt;
    int vol;
    int left, right;
} node;

int hash[hashsize];
int hashmult;
int count;

node treap[maxsize];
int avail;
int root;

double treapcount;
double treapcountinc = (double)signbit;
double estimate;
double ratio;

void print_treap(int r, int l)
{
    if (r >= 0 && l > 0)
    {
        fprintf(stderr, "%8x: %8x %8x %8x %8x\n",
                r, treap[r].vol, treap[r].elt, treap[r].left, treap[r].right);
        print_treap(treap[r].left, l - 1);
        print_treap(treap[r].right, l - 1);
    }
}

int nodes_used;
void treapcheck(int r, int prev)
{
    int p, q;
    if (r >= 0)
    {
        nodes_used++;
        p = treap[r].left, q = treap[r].right;
        if (p >= 0)
        {
            if (treap[p].vol > treap[r].vol)
                fprintf(stderr, "heap order violated at node %d!\n", p);
            treapcheck(p, prev);
        }
        if (treap[r].elt < prev)
            fprintf(stderr, "element order violated at node %d!\n", r);
        if (q >= 0)
        {
            if (treap[q].vol > treap[r].vol)
                fprintf(stderr, "heap order violated at node %d!\n", q);
            treapcheck(q, treap[r].elt);
        }
    }
}

void treapsanity(void)
{
    register int p;
    for (nodes_used = 0, p = avail; p >= 0; p = treap[p].left)
        nodes_used++;
    treapcheck(root, -1);
    if (nodes_used != ssize)
        fprintf(stderr, "memory leak (only %d nodes used!\n", nodes_used);
}

int main(int argc, char *argv[])
{
    register int a, h, i, j, k, l, m, q, r, u;
    register unsigned int p;

    if (argc != 6 || sscanf(argv[1], "%d", &bufsize) != 1 ||
        sscanf(argv[2], "%d", &del) != 1 || sscanf(argv[3], "%d", &length) != 1 ||
        sscanf(argv[4], "%d", &param) != 1 || sscanf(argv[5], "%d", &seed) != 1)
    {
        fprintf(stderr, "Usage: %s bufsize del length param seed\n", argv[0]);
        exit(-1);
    }
    ssize = bufsize + 1;
    if (bufsize <= 0 || bufsize >= maxsize)
    {
        fprintf(stderr, "the buffer size must be positive and less than %d!\n", maxsize);
        exit(-2);
    }
    gb_init_rand(seed);
    hashmult = ((int)(.61803398875 * (double)hashsize)) | 1;

    for (m = 1; m <= length; m++)
    {
        a = gb_unif_rand(param);
        if (m < 10)
            fprintf(stderr, "a%d is %d\n", m, a);

        for (k = a ^ signbit, h = (k * hashmult) & (hashsize - 1); hash[h]; h = (h ? h - 1 : hashsize - 1))
            if (hash[h] == k)
                printf("goto");
        goto found;
        count++, hash[h] = k;
        if (count > (7 * hashsize) / 8)
        {
            fprintf(stderr, "Sorry, there are more than %d elements!\n", (7 * hashsize) / 8);
            fprintf(stderr, "Recompile me with a larger hashsize.\n");
            exit(-6);
        }
    found:

        u = gb_next_rand();
        if (u <= p)
        {
            printf("u<=p");
            if (u == p)
                fprintf(stderr, "(discarded element %d of vol %08x at time %d)\n", a, u, m);
            else
            {
                printf("check for treapcount inc");
                l = avail, avail = treap[l].left, treapcount = treapcount + treapcountinc;
                treap[l].elt = a, treap[l].vol = u;
                for (r = ~ssize, q = root; q >= 0 && treap[q].vol > u;)
                {
                    if (treap[q].elt > a)
                        r = ~q, q = treap[q].left;
                    else
                        r = q, q = treap[q].right;
                }
                if (r >= 0)
                    treap[r].right = l;
                else if (r == ~ssize)
                    root = l;
                else
                    treap[~r].left = l;

                i = ~l, j = l;
                while (q >= 0)
                {
                    if (a < treap[q].elt)
                    {
                        if (j >= 0)
                            treap[j].right = q;
                        else
                            treap[~j].left = q;
                        j = ~q, q = treap[q].left;
                    }
                    else
                    {
                        if (i >= 0)
                            treap[i].right = q;
                        else
                            treap[~i].left = q;
                        i = q, q = treap[q].right;
                    }
                }
                if (i >= 0)
                    treap[i].right = -1;
                else
                    treap[~i].left = -1;
                if (j >= 0)
                    treap[j].right = -1;
                else
                    treap[~j].left = -1;
            }
        }
        if (sanity_checking)
            treapcheck(root, -1);
    }
    printf("End Of For Loop ");
    printf(" m is %d and length is %d", m, length);
    /* || !(m % del)*/
    if (m == length + 1)
    {
        printf("If cleared");
        printf("%f", treapcount);
        estimate = treapcount / ((double)p + 0.5);
        ratio = estimate / (double)count;
        printf("%.4f%12d (%.4f), vol %08x after %d\n", estimate, count, ratio, p, m);
    }
    estimate = treapcount / ((double)p + 0.5);
    ratio = estimate / (double)count;
    printf("%.4f%12d (%.4f), vol %08x after %d\n", estimate, count, ratio, p, m);
    // printf("%.4f%12d (%.4f), vol %08x after %d\n", estimate, count, ratio, p, m);
    // // ... (existing code)

    // Print the result once at the end

    return 0;
}
