<template>
    <div class="form_radio_btn">
        <input type="button" :value="value" class="r-choose_input checkbox_x" :name="`${fieldName}`" :id="`${fieldName}${value}`"
                     :class="{ selected: selectedField === value }" @click="handleClick(value)">
        <label :for="`${fieldName}${value}`">{{ value }}</label>
    </div>
</template>

<script>
    export default {
        props: {
            selectedField: Number,
            fieldName: String,
            value: Number,
            handleClick: Function
        }
    }
</script>
