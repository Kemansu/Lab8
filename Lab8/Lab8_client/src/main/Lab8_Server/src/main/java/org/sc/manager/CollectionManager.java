package org.sc.manager;

import org.sc.data.Flat;

import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *  Данный класс отвечает за взаимодействие с коллекцией
 *  Содержит коллекцию команд
 *
 * @author Kemansu
 * @since 1.0
 */

public class CollectionManager {
    private final static ReadWriteLock lock = new ReentrantReadWriteLock();
    private static ArrayDeque<Flat> arrayDeque;

    private static LocalDate date;

    public CollectionManager() {
        arrayDeque = new ArrayDeque<>();
        date = LocalDate.now();
    }

    public static Flat getFlatById(Long id) {
        lock.readLock().lock(); // блокировка чтения
        try {
            for (Flat flat : arrayDeque) {
                if (flat.getId().equals(id)) {
                    return flat;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.readLock().unlock(); // снимаем блокировку
        }
        return null;
    }

    public static Boolean isIdExist(Long id){
        for (Flat flat : arrayDeque) {
            if (flat.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public static LocalDate getInitDate() {
        return date;
    }

    public static void setArrayDeque(ArrayDeque<Flat> arrayDeque) {
        lock.writeLock().lock();
        try {
            CollectionManager.arrayDeque = arrayDeque;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public static void add(Flat flat) {
        lock.writeLock().lock();
        try {
            arrayDeque.add(flat);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public static void clear() {
        lock.writeLock().lock();
        try {
            arrayDeque.clear();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public static ArrayDeque<Flat> getArrayDeque() {
        lock.readLock().lock();
        try {
            return arrayDeque;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            lock.readLock().unlock();
        }
    }
}
