<template>
    <div class="gui-element" id="input-block">
        <form id="form">
            <label id="x-label">Выберите X-координату</label>
                <div>
                    <div>
                        <div class="form_radio_btn">
                            <input type="button" value="-4" class="r-choose_input checkbox_x" name="x"
                                   id="checkbox-1">
                            <label for="checkbox-1">-4</label>
                        </div>

                        <div class="form_radio_btn">
                            <input type="button" value="-3" class="r-choose_input checkbox_x" name="x"
                                   id="checkbox-2">
                            <label for="checkbox-2">-3</label>
                        </div>

                        <div class="form_radio_btn">
                            <input type="button" value="-2" class="r-choose_input checkbox_x" name="x"
                                   id="checkbox-3">
                            <label for="checkbox-3">-2</label>
                        </div>

                        <div class="form_radio_btn">
                            <input type="button" value="-1" class="r-choose_input checkbox_x" name="x"
                                   id="checkbox-4">
                            <label for="checkbox-4">-1</label>
                        </div>

                        <div class="form_radio_btn">
                            <input type="button" value="0" class="r-choose_input checkbox_x" name="x"
                                   id="checkbox-5">
                            <label for="checkbox-5">0</label>
                        </div>

                        <div class="form_radio_btn">
                            <input type="button" value="1" class="r-choose_input checkbox_x" name="x"
                                   id="checkbox-6">
                            <label for="checkbox-6">1</label>
                        </div>

                        <div class="form_radio_btn">
                            <input type="button" value="2" class="r-choose_input checkbox_x" name="x"
                                   id="checkbox-7">
                            <label for="checkbox-7">2</label>
                        </div>

                        <div class="form_radio_btn">
                            <input type="button" value="3" class="r-choose_input checkbox_x" name="x"
                                   id="checkbox-8">
                            <label for="checkbox-8">3</label>
                        </div>

                        <div class="form_radio_btn">
                            <input type="button" value="4" class="r-choose_input checkbox_x" name="x"
                                   id="checkbox-9">
                            <label for="checkbox-9">4</label>
                        </div>
                    </div>
                </div>
                <label id="y-label" for="y-inp">Выберите Y-координату</label>
                <div>
                    <input type="text" placeholder="Введите Y в (-3...3)" class="y-choose_input" id="y-inp" name="y" />
                </div>
                <label id="r-label">Выберите R</label>
                <div>
                    <!-- <div class="form_radio_btn">
                        <h:selectOneRadio value="#{controllerBean.graphShot.r}" class="r-choose_input checkbox_x" name="r" style="display: none">
                            <f:selectItem itemValue="1" itemLabel="1"/>
                            <f:selectItem itemValue="1.5" itemLabel="1.5"/>
                            <f:selectItem itemValue="2" itemLabel="2"/>
                            <f:selectItem itemValue="2.5" itemLabel="2.5"/>
                            <f:selectItem itemValue="3" itemLabel="3"/>
                        </h:selectOneRadio>
                    </div> -->
                </div>
                <input type="submit" value="Отправить" class="submit_button" id="submitButton"/>
                <p class="error_text" id="Incorrect">Некорректные значения в полях формы.</p>
                <p class="error_text" id="rNotSelectedError">r не выбран, невозвожно определить координаты</p>
            </form>
        </div>
</template>