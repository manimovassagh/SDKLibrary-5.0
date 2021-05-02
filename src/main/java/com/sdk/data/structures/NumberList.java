package com.sdk.data.structures;

import com.sdk.data.types.Numbers;
import com.sdk.data.types.Strings;
import com.sdk.storage.file.TextFile;
import com.sdk.tools.ExternalTools;
import java.io.IOException;

import java.io.Serializable;
import java.util.*;

public class NumberList<E extends Number> implements Iterable<E>, Serializable {

	private ArrayList<Number> list;
    private boolean duplicates;
    
	private static final long serialVersionUID = 1L;
    private int index;

    public NumberList() {
        duplicates = true;
        list = new ArrayList<>();
        index = 0;
    }

    public Number get(int index) {
        return list.get(index);
    }

    public void set(Number value, int index) {
        list.set(index, value);
    }

    public Number[] toArray() {
        return list.toArray(new Number[0]);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void clear() {
        list.clear();
    }

    public int size() {
        return list.size();
    }

    public boolean contains(Number value) {
        return list.contains(value);
    }

    public int countDuplicates(String key) {
        return Collections.frequency(list, key);
    }

    public boolean eraseDuplicates() {
        try {
            list = new ArrayList<>(new HashSet<>(list));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public NumberList(boolean duplicates) {
        this.duplicates = duplicates;
        list = new ArrayList<>();
    }

    public NumberList<E> add(Number value) {
        list.add(value);
        if (!duplicates) {
            eraseDuplicates();
        }

        return this;
    }

    public NumberList<E> add(Number[] numbers) {
        list.addAll(Arrays.asList(numbers));
        if (!duplicates) {
            eraseDuplicates();
        }

        return this;
    }

    public NumberList<E> addAll(NumberList<E> list) {
        this.list.addAll(Arrays.asList(list.toArray()));
        if (!duplicates) {
            eraseDuplicates();
        }

        return this;
    }

    public boolean remove(int index) {
        try {
            list.remove(index);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean remove(Number value) {
        return list.remove(value);
    }

    public void sort() {
        list.sort(Collections.reverseOrder());
        Collections.reverse(list);
    }

    public int indexOf(Number value) {
        return list.indexOf(value);
    }

    public double sum() {
        double sum = 0;

        for (Number number : list) {
            sum += Double.parseDouble(String.valueOf(number));
        }

        return sum;
    }

    public Number[] getEvens() {
        List<Number> evens = new ArrayList<>();
        list.forEach(number -> {
            if (number.longValue() % 2 == 0) {
                evens.add(number);
            }
        });

        return evens.toArray(new Number[0]);
    }

    public Number[] getOdds() {
        List<Number> odds = new ArrayList<>();
        list.forEach(number -> {
            if (number.longValue() % 2 != 0) {
                odds.add(number);
            }
        });

        return odds.toArray(new Number[0]);
    }

    public Number[] getPrimes() {
        return null;
    }

    public Number[] getPerfects() {
        List<Number> perfects = new ArrayList<>();

        for (Number number : list) {
            if (new Numbers().isPerfect(number.intValue())) {
                perfects.add(number);
            }
        }

        return perfects.toArray(new Number[0]);
    }

    public void pow(double value) {
        for (int i = 0; i < list.size(); i++) {
            list.set(i, Math.pow(list.get(i).doubleValue(), value));
        }
    }

    public void sum(double value) {
        for (int i = 0; i < list.size(); i++) {
            list.set(i, list.get(i).doubleValue() + 1);
        }
    }

    public void reverse() {
        Collections.reverse(list);
    }

    public boolean write(String path) {
        try {
            TextFile file = new TextFile(path);

            if (!file.exists()) {
                if (!file.create()) {
                    return false;
                }
            }

            Number[] numbers = toArray();
            List<String> items = new ArrayList<>();

            for (Number n : numbers) {
                items.add(n.toString());
            }

            return file.write(items.toArray(new String[0]));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean read(String path, boolean erase, boolean invalidNumber) {
        try {
            TextFile file = new TextFile(path);

            if (file.exists()) {
                if (erase) {
                    list.clear();
                }

                file.readAllLines().forEach(line -> {
                    if (!invalidNumber) {
                        if (new Strings().isNumber(line)) {
                            add(Double.parseDouble(line));
                        }
                    } else {
                        add(Double.parseDouble(line));
                    }
                });
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean append(String path) {
        try {
            TextFile file = new TextFile(path);

            if (file.exists()) {
                for (int i = 0; i < list.size(); i++) {
                    file.append(String.valueOf(list.get(i)), true);
                }
                
                return true;
            }

            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Number getBiggest() {
        return list.stream().max(Comparator.comparingDouble(Number::doubleValue)).get();
    }

    public Number getSmallest() {
        return list.stream().min(Comparator.comparingDouble(Number::doubleValue)).get();
    }

    public int getBiggestIndex() {
        return list.indexOf(list.stream().max(Comparator.comparingDouble(Number::doubleValue)).get());
    }

    public int getSmallestIndex() {
        return list.indexOf(list.stream().min(Comparator.comparingDouble(Number::doubleValue)).get());
    }

    public Number[] inRangeOf(Number start, Number end, boolean sort) {
        List<Number> numbers = new ArrayList<>();

        for (Number number : list) {
            if (number.doubleValue() >= start.doubleValue()
                    && number.doubleValue() <= end.doubleValue()) {
                numbers.add(number);
            }
        }

        if (sort) {
            numbers.sort(Collections.reverseOrder());
            Collections.reverse(numbers);
        }

        return numbers.toArray(new Number[0]);
    }

    public void printList(String start, boolean number) {
        for (int i = 0; i < list.size(); i++) {
            if (!new Strings().isNullOrEmpty(start)) {
                System.out.print(start);
            }

            if (number) {
                System.out.print("[" + (i + 1) + "] ");
            }

            System.out.println(list.get(i));
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (Objects.isNull(obj)) {
            return false;
        }

        if (obj.getClass() != getClass()) {
            return false;
        }

        List<Number> firstList = Arrays.asList(((NumberList) obj).toArray()),
                secondList = Arrays.asList(this.toArray());

        if (firstList.size() != secondList.size()) {
            return false;
        }

        for (Number n : firstList) {
            if (!secondList.contains(n)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");

        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));

            if ((i + 1) < list.size()) {
                sb.append(",");
            }
        }

        return sb.append("]").toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            @Override
            public boolean hasNext() {
                return index < list.size();
            }

            @Override
            public E next() {
                return (E) list.get(index++);
            }
        };
    }
}
