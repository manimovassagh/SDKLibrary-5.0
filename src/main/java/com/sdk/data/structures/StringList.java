package com.sdk.data.structures;

import com.sdk.data.types.Strings;
import com.sdk.storage.file.TextFile;
import java.io.Serializable;
import java.util.*;

public class StringList implements Iterable<String>, Serializable {

	private ArrayList<String> list;
    private boolean duplicates;
    
	private static final long serialVersionUID = 1L;
    private int index;

    public StringList() {
        duplicates = true;
        list = new ArrayList<>();
        index = 0;
    }

    public StringList(boolean duplicates) {
        this.duplicates = duplicates;
        list = new ArrayList<>();
        index = 0;
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

    public String get(int index) {
        return list.get(index);
    }

    public void set(String value, int index) {
        list.set(index, value);
    }

    public String[] toArray() {
        return list.toArray(new String[0]);
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

    public boolean contains(String value) {
        return list.contains(value);
    }

    public StringList add(Object value) {
        list.add(value.toString());
        if (!duplicates) {
            eraseDuplicates();
        }

        return this;
    }

    public StringList add(String[] input) {
        list.addAll(Arrays.asList(input));
        if (!duplicates) {
            eraseDuplicates();
        }

        return this;
    }

    public StringList addAll(StringList list) {
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

    public boolean remove(String key) {
        return list.remove(key);
    }

    public void sort() {
        Collections.sort(list);
    }

    public int indexOf(String key) {
        return list.indexOf(key);
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

            return file.write(toArray());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean read(String path, boolean erase) {
        try {
            TextFile file = new TextFile(path);

            if (file.exists()) {
                if (erase) {
                    list.clear();
                }

                file.readAllLines().forEach(line -> {
                    add(line);
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
                return file.append(toArray());
            }

            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getBiggest() {
        return list.stream().max(Comparator.comparingInt(String::length)).get();
    }

    public String getSmallest() {
        return list.stream().min(Comparator.comparingInt(String::length)).get();
    }

    public int getBiggestIndex() {
        return list.indexOf(list.stream().max(Comparator.comparingInt(String::length)).get());
    }

    public int getSmallestIndex() {
        return list.indexOf(list.stream().min(Comparator.comparingInt(String::length)).get());
    }

    public void toLowerCase() {
        for (int i = 0; i < list.size(); i++) {
            list.set(i, list.get(i).toLowerCase());
        }
    }

    public void toUpperCase() {
        for (int i = 0; i < list.size(); i++) {
            list.set(i, list.get(i).toUpperCase());
        }
    }

    public void concat(String value) {
        if (new Strings().isNullOrEmpty(value)) {
            return;
        }

        for (int i = 0; i < list.size(); i++) {
            list.set(i, list.get(i).concat(value));
        }
    }

    public void concat(String value, int index) {
        if (new Strings().isNullOrEmpty(value)) {
            return;
        }

        list.set(index, list.get(index).concat(value));
    }

    public int getLength(int index) {
        return list.get(index).length();
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

        String[] firstList = ((StringList) obj).toArray(),
                secondList = toArray();

        Arrays.sort(firstList);
        Arrays.sort(secondList);

        return Arrays.equals(firstList, secondList);
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
    public Iterator<String> iterator() {
        return new Iterator<String>() {
            @Override
            public boolean hasNext() {
                return index < list.size();
            }

            @Override
            public String next() {
                return list.get(index++);
            }
        };
    }

    public boolean isDuplicates() {
        return duplicates;
    }

    public void setDuplicates(boolean duplicates) {
        this.duplicates = duplicates;
    }

    public ArrayList<String> getList() {
        return list;
    }
}
