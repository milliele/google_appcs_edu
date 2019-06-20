/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.touringmusician;


import android.graphics.Point;

import java.util.Iterator;

public class CircularLinkedList implements Iterable<Point> {

    private class Node {
        Point point;
        Node prev, next;
        /**
         **
         **  YOUR CODE GOES HERE
         **
         **/
        Node(Point p) {
            point = p;
            prev = null;
            next = null;
        }
    }

    Node head = null;

    public void insertBeginning(Point p) {
        /**
         **
         **  YOUR CODE GOES HERE
         **
         **/
        Node nd = new Node(p);
        if(null == head) {
            nd.next = nd;
            nd.prev = nd;
            head = nd;
        } else {
            insertBefore(nd, head);
            head = nd;
        }
    }

    private void insertBefore(Node cand, Node cur) {
        Node prev = cur.prev;
        cur.prev = cand;
        cand.next = cur;
        prev.next = cand;
        cand.prev = prev;
    }

    private float distanceBetween(Point from, Point to) {
        return (float) Math.sqrt(Math.pow(from.y-to.y, 2) + Math.pow(from.x-to.x, 2));
    }

    public float totalDistance() {
        float total = 0;
        /**
         **
         **  YOUR CODE GOES HERE
         **
         **/
        Node cur = head.next,prev =head;
        while(cur != head) {
            total += distanceBetween(prev.point,cur.point);
            prev = cur;
            cur = cur.next;
        }

        return total;
    }

    public void insertNearest(Point p) {
        /**
         **
         **  YOUR CODE GOES HERE
         **
         **/
        Node nd = new Node(p);
        if(null == head) {
            nd.next = nd;
            nd.prev = nd;
            head = nd;
        } else {
            Node nearest = head;
            float ndis = distanceBetween(nearest.point, p);
//            System.out.println(String.format("distance = %.2f",ndis));
            Node cur = head.next;
            while (cur!=head) {
                float dis = distanceBetween(p,cur.point);
//                System.out.println(String.format("distance = %.2f",ndis));
                if(dis<ndis) {
//                    System.out.println("change");
                    ndis = dis;
                    nearest = cur;
                }
                cur = cur.next;
            }
            Node after = nearest.next;
            insertBefore(nd,after);
        }
    }

    private float increseDistance(Point p, Node after) {
        if (after.prev == after) {
            return distanceBetween(p, after.point);
        } else {
            return distanceBetween(p, after.point) + distanceBetween(p,after.prev.point)
                    - distanceBetween(after.point,after.prev.point);
        }
    }

    public void insertSmallest(Point p) {
        /**
         **
         **  YOUR CODE GOES HERE
         **
         **/
        Node nd = new Node(p);
        if(null == head) {
            nd.next = nd;
            nd.prev = nd;
            head = nd;
        } else {
            Node smallest = head;
            float ndis = increseDistance(p, smallest);
            Node cur = head.next;
            while (cur!=head) {
                float dis = increseDistance(p, cur);
                if(dis<ndis) {
                    ndis = dis;
                    smallest= cur;
                }
                cur = cur.next;
            }
            insertBefore(nd,smallest);
            if (smallest == head) head = nd;
        }
    }

    public void reset() {
        head = null;
    }

    private class CircularLinkedListIterator implements Iterator<Point> {

        Node current;

        public CircularLinkedListIterator() {
            current = head;
        }

        @Override
        public boolean hasNext() {
            return (current != null);
        }

        @Override
        public Point next() {
            Point toReturn = current.point;
            current = current.next;
            if (current == head) {
                current = null;
            }
            return toReturn;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public Iterator<Point> iterator() {
        return new CircularLinkedListIterator();
    }


}
