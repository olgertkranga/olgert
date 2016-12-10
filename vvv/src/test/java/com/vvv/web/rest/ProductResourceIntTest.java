package com.vvv.web.rest;

import com.vvv.VvvApp;

import com.vvv.domain.Product;
import com.vvv.repository.ProductRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProductResource REST controller.
 *
 * @see ProductResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = VvvApp.class)
public class ProductResourceIntTest {

    private static final String DEFAULT_PRODUCT = "AAAAA";
    private static final String UPDATED_PRODUCT = "BBBBB";

    private static final Double DEFAULT_COST = 1D;
    private static final Double UPDATED_COST = 2D;

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final Integer DEFAULT_SQTY = 1;
    private static final Integer UPDATED_SQTY = 2;

    private static final Integer DEFAULT_MQTY = 1;
    private static final Integer UPDATED_MQTY = 2;

    private static final Integer DEFAULT_LQTY = 1;
    private static final Integer UPDATED_LQTY = 2;

    private static final Integer DEFAULT_XLQTY = 1;
    private static final Integer UPDATED_XLQTY = 2;

    private static final Integer DEFAULT_XXLQTY = 1;
    private static final Integer UPDATED_XXLQTY = 2;

    private static final String DEFAULT_CUR = "AAAAA";
    private static final String UPDATED_CUR = "BBBBB";

    private static final String DEFAULT_IMG_PATH = "AAAAA";
    private static final String UPDATED_IMG_PATH = "BBBBB";

    @Inject
    private ProductRepository productRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restProductMockMvc;

    private Product product;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductResource productResource = new ProductResource();
        ReflectionTestUtils.setField(productResource, "productRepository", productRepository);
        this.restProductMockMvc = MockMvcBuilders.standaloneSetup(productResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createEntity(EntityManager em) {
        Product product = new Product()
                .product(DEFAULT_PRODUCT)
                .cost(DEFAULT_COST)
                .price(DEFAULT_PRICE)
                .sqty(DEFAULT_SQTY)
                .mqty(DEFAULT_MQTY)
                .lqty(DEFAULT_LQTY)
                .xlqty(DEFAULT_XLQTY)
                .xxlqty(DEFAULT_XXLQTY)
                .cur(DEFAULT_CUR)
                .img_path(DEFAULT_IMG_PATH);
        return product;
    }

    @Before
    public void initTest() {
        product = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduct() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // Create the Product

        restProductMockMvc.perform(post("/api/products")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(product)))
                .andExpect(status().isCreated());

        // Validate the Product in the database
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(databaseSizeBeforeCreate + 1);
        Product testProduct = products.get(products.size() - 1);
        assertThat(testProduct.getProduct()).isEqualTo(DEFAULT_PRODUCT);
        assertThat(testProduct.getCost()).isEqualTo(DEFAULT_COST);
        assertThat(testProduct.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testProduct.getSqty()).isEqualTo(DEFAULT_SQTY);
        assertThat(testProduct.getMqty()).isEqualTo(DEFAULT_MQTY);
        assertThat(testProduct.getLqty()).isEqualTo(DEFAULT_LQTY);
        assertThat(testProduct.getXlqty()).isEqualTo(DEFAULT_XLQTY);
        assertThat(testProduct.getXxlqty()).isEqualTo(DEFAULT_XXLQTY);
        assertThat(testProduct.getCur()).isEqualTo(DEFAULT_CUR);
        assertThat(testProduct.getImg_path()).isEqualTo(DEFAULT_IMG_PATH);
    }

    @Test
    @Transactional
    public void getAllProducts() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the products
        restProductMockMvc.perform(get("/api/products?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
                .andExpect(jsonPath("$.[*].product").value(hasItem(DEFAULT_PRODUCT.toString())))
                .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.doubleValue())))
                .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
                .andExpect(jsonPath("$.[*].sqty").value(hasItem(DEFAULT_SQTY)))
                .andExpect(jsonPath("$.[*].mqty").value(hasItem(DEFAULT_MQTY)))
                .andExpect(jsonPath("$.[*].lqty").value(hasItem(DEFAULT_LQTY)))
                .andExpect(jsonPath("$.[*].xlqty").value(hasItem(DEFAULT_XLQTY)))
                .andExpect(jsonPath("$.[*].xxlqty").value(hasItem(DEFAULT_XXLQTY)))
                .andExpect(jsonPath("$.[*].cur").value(hasItem(DEFAULT_CUR.toString())))
                .andExpect(jsonPath("$.[*].img_path").value(hasItem(DEFAULT_IMG_PATH.toString())));
    }

    @Test
    @Transactional
    public void getProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(product.getId().intValue()))
            .andExpect(jsonPath("$.product").value(DEFAULT_PRODUCT.toString()))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST.doubleValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.sqty").value(DEFAULT_SQTY))
            .andExpect(jsonPath("$.mqty").value(DEFAULT_MQTY))
            .andExpect(jsonPath("$.lqty").value(DEFAULT_LQTY))
            .andExpect(jsonPath("$.xlqty").value(DEFAULT_XLQTY))
            .andExpect(jsonPath("$.xxlqty").value(DEFAULT_XXLQTY))
            .andExpect(jsonPath("$.cur").value(DEFAULT_CUR.toString()))
            .andExpect(jsonPath("$.img_path").value(DEFAULT_IMG_PATH.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProduct() throws Exception {
        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product
        Product updatedProduct = productRepository.findOne(product.getId());
        updatedProduct
                .product(UPDATED_PRODUCT)
                .cost(UPDATED_COST)
                .price(UPDATED_PRICE)
                .sqty(UPDATED_SQTY)
                .mqty(UPDATED_MQTY)
                .lqty(UPDATED_LQTY)
                .xlqty(UPDATED_XLQTY)
                .xxlqty(UPDATED_XXLQTY)
                .cur(UPDATED_CUR)
                .img_path(UPDATED_IMG_PATH);

        restProductMockMvc.perform(put("/api/products")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedProduct)))
                .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = products.get(products.size() - 1);
        assertThat(testProduct.getProduct()).isEqualTo(UPDATED_PRODUCT);
        assertThat(testProduct.getCost()).isEqualTo(UPDATED_COST);
        assertThat(testProduct.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testProduct.getSqty()).isEqualTo(UPDATED_SQTY);
        assertThat(testProduct.getMqty()).isEqualTo(UPDATED_MQTY);
        assertThat(testProduct.getLqty()).isEqualTo(UPDATED_LQTY);
        assertThat(testProduct.getXlqty()).isEqualTo(UPDATED_XLQTY);
        assertThat(testProduct.getXxlqty()).isEqualTo(UPDATED_XXLQTY);
        assertThat(testProduct.getCur()).isEqualTo(UPDATED_CUR);
        assertThat(testProduct.getImg_path()).isEqualTo(UPDATED_IMG_PATH);
    }

    @Test
    @Transactional
    public void deleteProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        int databaseSizeBeforeDelete = productRepository.findAll().size();

        // Get the product
        restProductMockMvc.perform(delete("/api/products/{id}", product.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(databaseSizeBeforeDelete - 1);
    }
}
