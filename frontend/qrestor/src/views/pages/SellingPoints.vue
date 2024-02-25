<script setup>
import { FilterMatchMode } from 'primevue/api';
import { ref, onMounted, onBeforeMount } from 'vue';
import SellingPointsService from '@/service/SellingPointsService.js';
import { useToast } from 'primevue/usetoast';

const toast = useToast();

const points = ref(null);
const pointDialog = ref(false);
const deletePointDialog = ref(false);
const deletePointsDialog = ref(false);
const point = ref({});
const selectedPoints = ref(null);
const dt = ref(null);
const filters = ref({});
const submitted = ref(false);

const sellingPointService = new SellingPointsService();

onBeforeMount(() => {
    initFilters();
});
onMounted(async () => {
    const {data} = await sellingPointService.getSellingPoints();
    points.value = data;
});
const formatCurrency = (value) => {
    return value.toLocaleString('en-US', { style: 'currency', currency: 'USD' });
};

const openNew = () => {
    point.value = {};
    submitted.value = false;
    pointDialog.value = true;
};

const hideDialog = () => {
    pointDialog.value = false;
    submitted.value = false;
};

const savePoint = () => {
    submitted.value = true;
    if (point.value.name && point.value.name.trim() && point.value.price) {
        if (point.value.id) {
            point.value.inventoryStatus = point.value.inventoryStatus.value ? point.value.inventoryStatus.value : point.value.inventoryStatus;
            points.value[findIndexById(point.value.id)] = point.value;
            toast.add({ severity: 'success', summary: 'Successful', detail: 'Selling point updated', life: 3000 });
        } else {
            point.value.id = createId();
            point.value.code = createId();
            point.value.image = 'point-placeholder.svg';
            point.value.inventoryStatus = point.value.inventoryStatus ? point.value.inventoryStatus.value : 'INSTOCK';
            points.value.push(point.value);
            toast.add({ severity: 'success', summary: 'Successful', detail: 'Selling point Created', life: 3000 });
        }
        pointDialog.value = false;
        point.value = {};
    }
};

const editPoint = (editPoint) => {
    point.value = { ...editPoint };
    console.log(point);
    pointDialog.value = true;
};

const confirmDeletePoint = (editPoint) => {
    point.value = editPoint;
    deletePointDialog.value = true;
};

const deletePoint = () => {
    points.value = points.value.filter((val) => val.id !== point.value.id);
    deletePointDialog.value = false;
    point.value = {};
    toast.add({ severity: 'success', summary: 'Successful', detail: 'Selling point deleted', life: 3000 });
};

const findIndexById = (id) => {
    let index = -1;
    for (let i = 0; i < points.value.length; i++) {
        if (points.value[i].id === id) {
            index = i;
            break;
        }
    }
    return index;
};

const createId = () => {
    let id = '';
    const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    for (let i = 0; i < 5; i++) {
        id += chars.charAt(Math.floor(Math.random() * chars.length));
    }
    return id;
};

const makeIdShorter = (id) => {
    return id.substring(0, 8) + ' ...';
};

const exportCSV = () => {
    dt.value.exportCSV();
};

const confirmDeleteSelected = () => {
    deletePointsDialog.value = true;
};
const deleteSelectedPoints = () => {
    points.value = points.value.filter((val) => !selectedPoints.value.includes(val));
    deletePointsDialog.value = false;
    selectedPoints.value = null;
    toast.add({ severity: 'success', summary: 'Successful', detail: 'Selling points deleted', life: 3000 });
};

const initFilters = () => {
    filters.value = {
        global: { value: null, matchMode: FilterMatchMode.CONTAINS }
    };
};
</script>

<template>
    <div class="grid">
        <div class="col-12">
            <div class="card">
                <Toast />
                <Toolbar class="mb-4">
                    <template v-slot:start>
                        <div class="my-2">
                            <Button label="New" icon="pi pi-plus" class="p-button-success mr-2" @click="openNew" />
                            <Button label="Delete" icon="pi pi-trash" class="p-button-danger" @click="confirmDeleteSelected" :disabled="!selectedPoints || !selectedPoints.length" />
                        </div>
                    </template>

                    <template v-slot:end>
                        <FileUpload mode="basic" accept="image/*" :maxFileSize="1000000" label="Import" chooseLabel="Import" class="mr-2 inline-block" />
                        <Button label="Export" icon="pi pi-upload" class="p-button-help" @click="exportCSV($event)" />
                    </template>
                </Toolbar>

                <DataTable
                    ref="dt"
                    :value="points"
                    v-model:selection="selectedPoints"
                    dataKey="id"
                    :paginator="true"
                    :rows="10"
                    :filters="filters"
                    paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
                    :rowsPerPageOptions="[5, 10, 25]"
                    currentPageReportTemplate="Showing {first} to {last} of {totalRecords} selling points"
                    responsiveLayout="scroll"
                >
                    <template #header>
                        <div class="flex flex-column md:flex-row md:justify-content-between md:align-items-center">
                            <h5 class="m-0">Manage Selling points</h5>
                            <span class="block mt-2 md:mt-0 p-input-icon-left">
                                <i class="pi pi-search" />
                                <InputText v-model="filters['global'].value" placeholder="Search..." />
                            </span>
                        </div>
                    </template>

                    <Column selectionMode="multiple" headerStyle="width: 3rem"></Column>
                    <Column field="publicId" header="Id" :sortable="true" headerStyle="width:14%; min-width:10rem;">
                        <template #body="slotProps">
                            <span class="p-column-title">Id</span>
                            {{ makeIdShorter(slotProps.data.publicId) }}
                        </template>
                    </Column>
                    <Column field="name" header="Name" :sortable="true" headerStyle="width:14%; min-width:10rem;">
                        <template #body="slotProps">
                            <span class="p-column-title">Name</span>
                            {{ slotProps.data.name }}
                        </template>
                    </Column>
                    <Column field="title" header="Title" :sortable="true" headerStyle="width:14%; min-width:8rem;">
                        <template #body="slotProps">
                            <span class="p-column-title">Title</span>
                            {{ formatCurrency(slotProps.data.title) }}
                        </template>
                    </Column>
                    <Column field="description" header="Description" :sortable="true" headerStyle="width:14%; min-width:10rem;">
                        <template #body="slotProps">
                            <span class="p-column-title">Description</span>
                            {{ slotProps.data.description }}
                        </template>
                    </Column>
                  <Column field="address" header="Address" :sortable="true" headerStyle="width:14%; min-width:10rem;">
                    <template #body="slotProps">
                      <span class="p-column-title">Address</span>
                      {{ slotProps.data.address }}
                    </template>
                  </Column>
                  <Column field="phone" header="Phone" :sortable="true" headerStyle="width:14%; min-width:10rem;">
                    <template #body="slotProps">
                      <span class="p-column-title">Phone</span>
                      {{ slotProps.data.phone }}
                    </template>
                  </Column>
<!--                  <Column header="Menu background" headerStyle="width:14%; min-width:10rem;">-->
<!--                    <template #body="slotProps">-->
<!--                      <span class="p-column-title">Menu background</span>-->
<!--                      <img :src="slotProps.data.settings.backgroundImageUrl" :alt="slotProps.data.settings.backgroundImageUrl" class="shadow-2" width="100" />-->
<!--                    </template>-->
<!--                  </Column>-->
                    <Column headerStyle="min-width:10rem;">
                        <template #body="slotProps">
                            <Button icon="pi pi-pencil" class="p-button-rounded p-button-success mr-2" @click="editPoint(slotProps.data)" />
                            <Button icon="pi pi-trash" class="p-button-rounded p-button-warning mt-2" @click="confirmDeletePoint(slotProps.data)" />
                        </template>
                    </Column>
                </DataTable>

                <Dialog v-model:visible="pointDialog" :style="{ width: '450px' }" header="Selling point details" :modal="true" class="p-fluid">
                    <img :src="'/demo/images/product/' + point.image" :alt="point.image" v-if="point.image" width="150" class="mt-0 mx-auto mb-5 block shadow-2" />
                    <div class="field">
                        <label for="name">Name</label>
                        <InputText id="name" v-model.trim="point.name" required="true" autofocus :class="{ 'p-invalid': submitted && !point.name }" />
                        <small class="p-invalid" v-if="submitted && !point.name">Name is required.</small>
                    </div>
                    <div class="field">
                        <label for="description">Description</label>
                        <Textarea id="description" v-model="point.description" required="true" rows="3" cols="20" />
                    </div>

<!--                    <div class="field">-->
<!--                        <label for="inventoryStatus" class="mb-3">Inventory Status</label>-->
<!--                        <Dropdown id="inventoryStatus" v-model="point.inventoryStatus" :options="statuses" optionLabel="label" placeholder="Select a Status">-->
<!--                            <template #value="slotProps">-->
<!--                                <div v-if="slotProps.value && slotProps.value.value">-->
<!--                                    <span :class="'point-badge status-' + slotProps.value.value">{{ slotProps.value.label }}</span>-->
<!--                                </div>-->
<!--                                <div v-else-if="slotProps.value && !slotProps.value.value">-->
<!--                                    <span :class="'point-badge status-' + slotProps.value.toLowerCase()">{{ slotProps.value }}</span>-->
<!--                                </div>-->
<!--                                <span v-else>-->
<!--                                    {{ slotProps.placeholder }}-->
<!--                                </span>-->
<!--                            </template>-->
<!--                        </Dropdown>-->
<!--                    </div>-->

                    <div class="field">
                        <label class="mb-3">Category</label>
                        <div class="formgrid grid">
                            <div class="field-radiobutton col-6">
                                <RadioButton id="category1" name="category" value="Accessories" v-model="point.category" />
                                <label for="category1">Accessories</label>
                            </div>
                            <div class="field-radiobutton col-6">
                                <RadioButton id="category2" name="category" value="Clothing" v-model="point.category" />
                                <label for="category2">Clothing</label>
                            </div>
                            <div class="field-radiobutton col-6">
                                <RadioButton id="category3" name="category" value="Electronics" v-model="point.category" />
                                <label for="category3">Electronics</label>
                            </div>
                            <div class="field-radiobutton col-6">
                                <RadioButton id="category4" name="category" value="Fitness" v-model="point.category" />
                                <label for="category4">Fitness</label>
                            </div>
                        </div>
                    </div>

                    <div class="formgrid grid">
                        <div class="field col">
                            <label for="price">Price</label>
                            <InputNumber id="price" v-model="point.price" mode="currency" currency="USD" locale="en-US" :class="{ 'p-invalid': submitted && !point.price }" :required="true" />
                            <small class="p-invalid" v-if="submitted && !point.price">Price is required.</small>
                        </div>
                        <div class="field col">
                            <label for="quantity">Quantity</label>
                            <InputNumber id="quantity" v-model="point.quantity" integeronly />
                        </div>
                    </div>
                    <template #footer>
                        <Button label="Cancel" icon="pi pi-times" class="p-button-text" @click="hideDialog" />
                        <Button label="Save" icon="pi pi-check" class="p-button-text" @click="savePoint" />
                    </template>
                </Dialog>

                <Dialog v-model:visible="deletePointDialog" :style="{ width: '450px' }" header="Confirm" :modal="true">
                    <div class="flex align-items-center justify-content-center">
                        <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
                        <span v-if="point"
                            >Are you sure you want to delete <b>{{ point.name }}</b
                            >?</span
                        >
                    </div>
                    <template #footer>
                        <Button label="No" icon="pi pi-times" class="p-button-text" @click="deletePointDialog = false" />
                        <Button label="Yes" icon="pi pi-check" class="p-button-text" @click="deletePoint" />
                    </template>
                </Dialog>

                <Dialog v-model:visible="deletePointsDialog" :style="{ width: '450px' }" header="Confirm" :modal="true">
                    <div class="flex align-items-center justify-content-center">
                        <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
                        <span v-if="point">Are you sure you want to delete the selected points?</span>
                    </div>
                    <template #footer>
                        <Button label="No" icon="pi pi-times" class="p-button-text" @click="deletePointsDialog = false" />
                        <Button label="Yes" icon="pi pi-check" class="p-button-text" @click="deleteSelectedPoints" />
                    </template>
                </Dialog>
            </div>
        </div>
    </div>
</template>

<style scoped lang="scss"></style>
