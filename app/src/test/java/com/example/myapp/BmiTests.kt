package com.example.myapp

import com.example.myapp.bmi.*
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import java.lang.IllegalArgumentException

class BmiTests : ShouldSpec({

    context("Bmi.count") {
        var bmi: Bmi

        should("This should return a value") {
            bmi = BmiForCmKg(50.0, 170.0)
            String.format("%.2f", bmi.count()) shouldBe "17,30"

            bmi = BmiForCmKg(70.0, 180.0)
            String.format("%.2f", bmi.count()) shouldBe "21,60"

            bmi = BmiForCmKg(160.5, 210.3)
            String.format("%.2f", bmi.count()) shouldBe "36,29"

            bmi = BmiForCmKg(250.0, 250.0)
            String.format("%.2f", bmi.count()) shouldBe "40,00"

            bmi = BmiForCmKg(15.0, 100.0)
            String.format("%.2f", bmi.count()) shouldBe "15,00"

            bmi = BmiForInLb(160.5, 90.0)
            String.format("%.2f", bmi.count()) shouldBe "13,93"

            bmi = BmiForInLb(95.0, 50.0)
            String.format("%.2f", bmi.count()) shouldBe "26,71"

            bmi = BmiForInLb(33.0, 40.0)
            String.format("%.2f", bmi.count()) shouldBe "14,50"

            bmi = BmiForInLb(550.0, 100.0)
            String.format("%.2f", bmi.count()) shouldBe "38,67"
        }

        should("These values should be too high or low and return an exception") {
            var exception = shouldThrow<IllegalArgumentException> {
                bmi = BmiForCmKg(305.0, 170.0)
                bmi.count()
            }

            exception = shouldThrow<IllegalArgumentException> {
                bmi = BmiForCmKg(100.0, 270.0)
                bmi.count()
            }

            exception = shouldThrow<IllegalArgumentException> {
                bmi = BmiForCmKg(10.0, 170.0)
                bmi.count()
            }

            exception = shouldThrow<IllegalArgumentException> {
                bmi = BmiForCmKg(55.0, 70.0)
                bmi.count()
            }

            exception = shouldThrow<IllegalArgumentException> {
                bmi = BmiForInLb(555.0, 70.0)
                bmi.count()
            }

            exception = shouldThrow<IllegalArgumentException> {
                bmi = BmiForInLb(-9.0, 70.0)
                bmi.count()
            }

            exception = shouldThrow<IllegalArgumentException> {
                bmi = BmiForInLb(150.0, 0.0)
                bmi.count()
            }

            exception = shouldThrow<IllegalArgumentException> {
                bmi = BmiForInLb(50.0, 970.0)
                bmi.count()
            }
        }
    }

})