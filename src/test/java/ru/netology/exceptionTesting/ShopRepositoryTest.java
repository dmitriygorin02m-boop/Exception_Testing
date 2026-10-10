
package ru.netology.exceptionTesting;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ShopRepositoryTest {

    // Сравнение объекта с самим собой
    @Test
    public void shouldReturnTrueWhenComparingObjectWithItself() {
        Product product = new Product(1, "Watch", 10000);

        assertEquals(product, product);
    }

    // Сравнение объекта с null
    @Test
    public void shouldReturnFalseWhenComparingWithNull() {
        Product product = new Product(1, "Watch", 10000);

        assertNotEquals(product, null);
    }

    // Сравнение объектов разных классов
    @Test
    public void shouldReturnFalseWhenObjectsHaveDifferentClasses() {
        Product product = new Product(1, "Watch", 10000);
        Object object = new Object();

        assertNotEquals(product, object);
    }

    // Сравнение одинаковых товаров
    @Test
    public void shouldReturnTrueWhenProductsAreEqual() {
        Product product1 = new Product(1, "Phone", 10000);
        Product product2 = new Product(1, "Phone", 10000);

        assertEquals(product1, product2);
    }

    // Сравнение товаров с разными идентификаторами
    @Test
    public void shouldReturnFalseWhenIdsAreDifferent() {
        Product product1 = new Product(1, "Phone", 10000);
        Product product2 = new Product(2, "Phone", 10000);

        assertNotEquals(product1, product2);
    }

    // Сравнение товаров с разными ценами
    @Test
    public void shouldReturnFalseWhenPricesAreDifferent() {
        Product product1 = new Product(1, "Phone", 10000);
        Product product2 = new Product(1, "Phone", 20000);

        assertNotEquals(product1, product2);
    }

    // Сравнение товаров с разными названиями
    @Test
    public void shouldReturnFalseWhenTitlesAreDifferent() {
        Product product1 = new Product(1, "Watch", 10000);
        Product product2 = new Product(1, "Screen", 10000);

        assertNotEquals(product1, product2);
    }

    // Одинаковый hashCode у равных товаров
    @Test
    public void shouldReturnSameHashCodeForEqualProducts() {
        Product product1 = new Product(1, "Phone", 10000);
        Product product2 = new Product(1, "Phone", 10000);

        assertEquals(product1.hashCode(), product2.hashCode());
    }

    // Успешное удаление существующего товара
    @Test
    public void shouldRemoveExistingProduct() {
        ShopRepository repository = new ShopRepository();

        Product product1 = new Product(1, "Watch", 15000);
        Product product2 = new Product(2, "Screen", 30000);

        repository.add(product1);
        repository.add(product2);

        repository.removeById(1);

        assertEquals(1, repository.findAll().length);
        assertEquals(2, repository.findAll()[0].getId());
    }

    // Генерация NotFoundException при удалении несуществующего товара
    @Test
    public void shouldThrowNotFoundExceptionWhenProductDoesNotExist() {
        ShopRepository repository = new ShopRepository();

        repository.add(new Product(1, "Watch", 10000));

        NotFoundException exception = assertThrows(
                NotFoundException.class,
                () -> repository.removeById(10)
        );

        assertEquals(
                "Element with id: 10 not found",
                exception.getMessage()
        );
    }

    // Добавление товара в пустой массив
    @Test
    public void shouldAddProductWhenRepositoryIsEmpty() {
        ShopRepository repository = new ShopRepository();
        Product product = new Product(1, "Watch", 15000);

        repository.add(product);

        Product[] actual = repository.findAll();

        assertEquals(1, actual.length);
        assertEquals(product, actual[0]);
    }

    // Копирования существующих товаров при добавлении нового
    @Test
    public void shouldPreserveExistingProductsWhenAddingAnother() {
        ShopRepository repository = new ShopRepository();
        Product product1 = new Product(1, "Watch", 15000);
        Product product2 = new Product(2, "Laptop", 75000);
        Product product3 = new Product(3, "Screen", 30000);

        repository.add(product1);
        repository.add(product2);
        repository.add(product3);

        Product[] actual = repository.findAll();

        assertEquals(3, actual.length);
        assertEquals(product1, actual[0]);
        assertEquals(product2, actual[1]);
        assertEquals(product3, actual[2]);
    }

    // Добавление товара в пустой репозиторий
    @Test
    public void shouldAddProductToEmptyRepository() {
        ShopRepository repository = new ShopRepository();
        Product product = new Product(1, "Watch", 15000);

        repository.add(product);

        assertEquals(1, repository.findAll().length);
        assertEquals(product, repository.findAll()[0]);
    }

    // Добавление товаров в непустой репозиторий
    @Test
    public void shouldCopyExistingProductsWhenAddingNewProduct() {
        ShopRepository repository = new ShopRepository();
        Product product1 = new Product(1, "Watch", 20000);
        Product product2 = new Product(2, "Laptop", 200000);
        Product product3 = new Product(3, "Screen", 40000);

        repository.add(product1);
        repository.add(product2);
        repository.add(product3);

        Product[] actual = repository.findAll();

        assertEquals(3, actual.length);
        assertEquals(product1, actual[0]);
        assertEquals(product2, actual[1]);
        assertEquals(product3, actual[2]);
    }

    // Удаление единственного товара из репозитория
    @Test
    public void shouldRemoveOnlyProduct() {
        ShopRepository repository = new ShopRepository();
        Product product = new Product(1, "Watch", 15000);
        repository.add(product);

        repository.remove(1);

        assertEquals(0, repository.findAll().length);
    }

    // Удаление первого товара из нескольких
    @Test
    public void shouldRemoveFirstProduct() {
        ShopRepository repository = new ShopRepository();
        Product product1 = new Product(1, "Watch", 15000);
        Product product2 = new Product(2, "Screen", 30000);
        repository.add(product1);
        repository.add(product2);

        repository.remove(1);

        assertEquals(1, repository.findAll().length);
        assertEquals(product2, repository.findAll()[0]);
    }

    // Удаление последнего товара из нескольких
    @Test
    public void shouldRemoveLastProduct() {
        ShopRepository repository = new ShopRepository();
        Product product1 = new Product(1, "Watch", 15000);
        Product product2 = new Product(2, "Screen", 30000);
        repository.add(product1);
        repository.add(product2);

        repository.remove(2);

        assertEquals(1, repository.findAll().length);
        assertEquals(product1, repository.findAll()[0]);
    }

    // Удаление товара из середины списка
    @Test
    public void shouldRemoveProductFromMiddle() {
        ShopRepository repository = new ShopRepository();
        Product product1 = new Product(1, "Watch", 15000);
        Product product2 = new Product(2, "Laptop", 100000);
        Product product3 = new Product(3, "Screen", 30000);
        repository.add(product1);
        repository.add(product2);
        repository.add(product3);

        repository.remove(2);

        assertEquals(2, repository.findAll().length);
        assertEquals(product1, repository.findAll()[0]);
        assertEquals(product3, repository.findAll()[1]);
    }

    //      -----   ТЕСТЫ ДЛЯ ЗАДАНИЯ 2 -----       //


    // Успешное добавление нового товара
    @Test
    public void shouldAddProduct() {
        ShopRepository repository = new ShopRepository();
        Product product = new Product(1, "Watch", 15000);

        repository.add(product);

        assertEquals(1, repository.findAll().length);
        assertEquals(product, repository.findAll()[0]);
    }

    // Исключение при добавлении товара с повторяющимся ID
    @Test
    public void shouldThrowAlreadyExistsExceptionWhenIdAlreadyExists() {
        ShopRepository repository = new ShopRepository();
        Product product1 = new Product(1, "Watch", 15000);
        Product product2 = new Product(1, "Screen", 30000);

        repository.add(product1);

        AlreadyExistsException exception = assertThrows(
                AlreadyExistsException.class,
                () -> repository.add(product2)
        );

        assertEquals(
                "Element with id: 1 already exists",
                exception.getMessage()
        );

        assertEquals(1, repository.findAll().length);
    }
}
