package com.example.firstcomposeapp.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.example.firstcomposeapp.ui.theme.ShimmerColorShades

@Composable
fun ShimmerAnimation(
) {
    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        )
    )
    val brush = Brush.linearGradient(
        colors = ShimmerColorShades,
        start = Offset(10f, 10f),
        end = Offset(translateAnim, translateAnim)
    )
    HeadShimmer(brush = brush)
    Spacer(modifier = Modifier
        .height(10.dp))
    LazyRow{
        repeat(3) {
            item {
                CardShimmer(brush = brush)
            }
        }
    }
    Spacer(modifier = Modifier
        .height(25.dp))
    LazyRow{
        repeat(3) {
            item {
                CardShimmer(brush = brush)
            }
        }
    }
    Spacer(modifier = Modifier
        .height(25.dp))
    LazyRow{
        repeat(3) {
            item {
                CardShimmer(brush = brush)
            }
        }
    }
}


@Composable
fun ShimmerItem(
    brush: Brush
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .size(250.dp)
                .background(brush = brush)
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .padding(vertical = 8.dp)
                .background(brush = brush)
        )
    }
}

@Composable
fun HeadShimmer(brush: Brush) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Spacer(
            modifier = Modifier
                .clip(RoundedCornerShape(25.dp))
                .height(38.dp)
                .width(38.dp)
                .padding(top = 0.dp)
                .background(brush = brush)
        )
        Spacer(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .padding(top = 2.dp)
                .background(brush = brush)
        )
        Spacer(
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .fillMaxWidth(0.87f)
                .height(30.dp)
                .padding(vertical = 8.dp)
                .background(brush = brush)
        )
        Spacer(
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .fillMaxWidth(0.9f)
                .width(420.dp)
                .height(120.dp)
                .background(brush = brush)
        )

    }
}


@Composable
fun CardShimmer(brush: Brush) {
    Column {
        Spacer(
            modifier = Modifier
                .padding(start = 20.dp)
                .clip(RoundedCornerShape(12.dp))
                .height(200.dp)
                .width(150.dp)
                .background(brush = brush)

        )
    }
}
