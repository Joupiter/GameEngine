package fr.joupi.games.engine.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
public class Pagination<T> {

    private final List<Page> pages;
    private final List<T> elements;

    private int elementsPerPage;

    public Pagination(int elementsPerPage) {
        this.pages = new ArrayList<>();
        this.elements = new ArrayList<>();
        this.elementsPerPage = elementsPerPage;
        this.generatePages();
    }

    public Pagination(Collection<T> elements, int elementsPerPage) {
        this(elementsPerPage);
        elements.forEach(this::addElement);
    }

    public void addElement(T element) {
        elements.add(element);

        if (getRequiredPages() > countPages()) {
            int index = elements.size() - 1;
            pages.add(new Page(countPages() + 1, index, index + elementsPerPage));
        }
    }

    public void removeElement(T element) {
        if (!elements.contains(element)) return;

        elements.remove(element);
        int countPages = countPages();

        if (countPages > 1 && getRequiredPages() < countPages)
            pages.remove(getLast());
    }

    public void removeElement(int index) {
        elements.remove(index);
        int countPages = countPages();

        if (countPages > 1 && getRequiredPages() < countPages)
            pages.remove(getLast());
    }

    public boolean containsElement(T element) {
        return elements.contains(element);
    }

    public int indexOf(T element) {
        return elements.indexOf(element);
    }

    public Page getLast() {
        return pages.getLast();
    }

    public Page getFirst() {
        return pages.getFirst();
    }

    public boolean contains(Page page) {
        return pages.contains(page);
    }

    public boolean isLast(Page page) {
        return page.equals(pages.getLast());
    }

    public boolean isFirst(Page page) {
        return page.equals(pages.getFirst());
    }

    public boolean hasNext(Page page) {
        return !isLast(page);
    }

    public boolean hasPrevious(Page page) {
        return !isFirst(page);
    }

    public Page getNext(Page page) {
        return hasNext(page) ? pages.get(pages.indexOf(page) + 1) : null;
    }

    public Page getPrevious(Page page) {
        return hasPrevious(page) ? pages.get(pages.indexOf(page) - 1) : null;
    }

    public Page getPage(int number) {
        return pages.get(number - 1);
    }

    public boolean hasPage(int number) {
        return number > 0 && number <= pages.size();
    }

    public int countPages() {
        return pages.size();
    }

    public void setElementsPerPage(int elementsPerPage) {
        this.elementsPerPage = elementsPerPage;
        generatePages();
    }

    public int countElements() {
        return elements.size();
    }

    private int getRequiredPages() {
        return (int) Math.ceil((double) elements.size() / elementsPerPage);
    }

    private void generatePages() {
        List<T> contents = new ArrayList<>(elements);

        pages.clear();
        elements.clear();
        contents.forEach(this::addElement);

        if (pages.isEmpty())
            pages.add(new Page(1, 0, elementsPerPage));
    }

    @Getter
    @AllArgsConstructor
    public class Page {

        private final int number, begin, end;

        public List<T> getElements() {
            return elements.subList(begin, Math.min(Pagination.this.countElements(), end));
        }

        public int countElements() {
            return elements.size();
        }

        public boolean contains(T element) {
            return elements.contains(element);
        }

    }

}