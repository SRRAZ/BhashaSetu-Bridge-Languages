package com.bhashasetu.app.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.bhashasetu.app.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom view for displaying pronunciation progress charts.
 * Supports line charts for progress tracking and bar charts for comparison.
 */
public class ChartView extends View {

    private static final int DEFAULT_LINE_COLOR = Color.parseColor("#2196F3");
    private static final int DEFAULT_GRID_COLOR = Color.parseColor("#E0E0E0");
    private static final int DEFAULT_TEXT_COLOR = Color.parseColor("#757575");
    private static final int DEFAULT_BACKGROUND_COLOR = Color.parseColor("#FFFFFF");
    
    private List<Float> data = new ArrayList<>();
    private List<String> labels = new ArrayList<>();
    
    private Paint linePaint;
    private Paint fillPaint;
    private Paint gridPaint;
    private Paint textPaint;
    private Paint backgroundPaint;
    
    private int paddingLeft = 50;
    private int paddingTop = 40;
    private int paddingRight = 20;
    private int paddingBottom = 60;
    
    private float maxValue = 100f;  // Default max value is 100 (for percentage)
    private float minValue = 0f;
    private boolean isBarChart = false;

    public ChartView(Context context) {
        super(context);
        init();
    }

    public ChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    
    private void init() {
        linePaint = new Paint();
        linePaint.setColor(DEFAULT_LINE_COLOR);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(4f);
        linePaint.setAntiAlias(true);
        
        fillPaint = new Paint();
        fillPaint.setColor(DEFAULT_LINE_COLOR);
        fillPaint.setAlpha(50);
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setAntiAlias(true);
        
        gridPaint = new Paint();
        gridPaint.setColor(DEFAULT_GRID_COLOR);
        gridPaint.setStyle(Paint.Style.STROKE);
        gridPaint.setStrokeWidth(1f);
        
        textPaint = new Paint();
        textPaint.setColor(DEFAULT_TEXT_COLOR);
        textPaint.setTextSize(24f);
        textPaint.setAntiAlias(true);
        
        backgroundPaint = new Paint();
        backgroundPaint.setColor(DEFAULT_BACKGROUND_COLOR);
        backgroundPaint.setStyle(Paint.Style.FILL);
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        // Draw background
        canvas.drawRect(0, 0, getWidth(), getHeight(), backgroundPaint);
        
        if (data.isEmpty()) {
            drawEmptyChart(canvas);
            return;
        }
        
        // Draw grid
        drawGrid(canvas);
        
        // Draw data
        if (isBarChart) {
            drawBarChart(canvas);
        } else {
            drawLineChart(canvas);
        }
        
        // Draw labels
        drawLabels(canvas);
    }
    
    private void drawEmptyChart(Canvas canvas) {
        String text = "No Data";
        Rect bounds = new Rect();
        textPaint.getTextBounds(text, 0, text.length(), bounds);
        float x = (getWidth() - bounds.width()) / 2f;
        float y = (getHeight() + bounds.height()) / 2f;
        canvas.drawText(text, x, y, textPaint);
    }
    
    private void drawGrid(Canvas canvas) {
        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;
        
        // Draw horizontal grid lines
        for (int i = 0; i <= 4; i++) {
            float y = paddingTop + height - (i * (height / 4f));
            canvas.drawLine(paddingLeft, y, paddingLeft + width, y, gridPaint);
            
            // Draw labels for y-axis
            String label = String.valueOf((int)(minValue + (i * ((maxValue - minValue) / 4))));
            float textWidth = textPaint.measureText(label);
            canvas.drawText(label, paddingLeft - textWidth - 10, y + 6, textPaint);
        }
        
        // Draw vertical grid lines
        int dataSize = data.size();
        if (dataSize > 1) {
            float xStep = width / (float)(dataSize - 1);
            for (int i = 0; i < dataSize; i++) {
                float x = paddingLeft + (i * xStep);
                canvas.drawLine(x, paddingTop, x, paddingTop + height, gridPaint);
            }
        }
    }
    
    private void drawLineChart(Canvas canvas) {
        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;
        float valueRange = maxValue - minValue;
        
        Path linePath = new Path();
        Path fillPath = new Path();
        
        int dataSize = data.size();
        if (dataSize > 0) {
            float xStep = width / (float)(Math.max(dataSize - 1, 1));
            
            // Start fill path at bottom
            fillPath.moveTo(paddingLeft, paddingTop + height);
            
            // First point
            float value = data.get(0);
            float x = paddingLeft;
            float y = paddingTop + height - ((value - minValue) / valueRange * height);
            linePath.moveTo(x, y);
            fillPath.lineTo(x, y);
            
            // Connect the points
            for (int i = 1; i < dataSize; i++) {
                value = data.get(i);
                x = paddingLeft + (i * xStep);
                y = paddingTop + height - ((value - minValue) / valueRange * height);
                linePath.lineTo(x, y);
                fillPath.lineTo(x, y);
            }
            
            // Close fill path
            fillPath.lineTo(paddingLeft + (dataSize - 1) * xStep, paddingTop + height);
            fillPath.close();
            
            // Draw paths
            canvas.drawPath(fillPath, fillPaint);
            canvas.drawPath(linePath, linePaint);
            
            // Draw points
            for (int i = 0; i < dataSize; i++) {
                value = data.get(i);
                x = paddingLeft + (i * xStep);
                y = paddingTop + height - ((value - minValue) / valueRange * height);
                canvas.drawCircle(x, y, 6f, linePaint);
            }
        }
    }
    
    private void drawBarChart(Canvas canvas) {
        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;
        float valueRange = maxValue - minValue;
        
        int dataSize = data.size();
        if (dataSize > 0) {
            float barWidth = width / (dataSize * 2); // Leave space between bars
            
            for (int i = 0; i < dataSize; i++) {
                float value = data.get(i);
                float barHeight = ((value - minValue) / valueRange) * height;
                float left = paddingLeft + (i * width / dataSize) + (barWidth / 2);
                float top = paddingTop + height - barHeight;
                float right = left + barWidth;
                float bottom = paddingTop + height;
                
                RectF rect = new RectF(left, top, right, bottom);
                canvas.drawRect(rect, fillPaint);
                canvas.drawRect(rect, linePaint);
            }
        }
    }
    
    private void drawLabels(Canvas canvas) {
        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;
        
        int dataSize = data.size();
        if (dataSize > 0 && labels.size() >= dataSize) {
            float xStep = width / (float)(dataSize);
            
            for (int i = 0; i < dataSize; i++) {
                String label = labels.get(i);
                float textWidth = textPaint.measureText(label);
                float x = paddingLeft + (i * xStep) + (xStep / 2) - (textWidth / 2);
                float y = paddingTop + height + 30;
                canvas.drawText(label, x, y, textPaint);
            }
        }
    }
    
    /**
     * Set the data for the chart
     * @param data List of data values
     * @param labels List of labels for the x-axis
     */
    public void setData(List<Float> data, List<String> labels) {
        this.data = data;
        this.labels = labels;
        invalidate();
    }
    
    /**
     * Set the range for the y-axis
     * @param min Minimum value
     * @param max Maximum value
     */
    public void setRange(float min, float max) {
        this.minValue = min;
        this.maxValue = max;
        invalidate();
    }
    
    /**
     * Set whether to display as a bar chart instead of a line chart
     * @param isBarChart true for bar chart, false for line chart
     */
    public void setBarChart(boolean isBarChart) {
        this.isBarChart = isBarChart;
        invalidate();
    }
    
    /**
     * Set the colors for the chart
     * @param lineColor Color for lines and bars
     * @param fillColor Color for the fill area
     * @param gridColor Color for the grid lines
     * @param textColor Color for the text
     */
    public void setColors(int lineColor, int fillColor, int gridColor, int textColor) {
        linePaint.setColor(lineColor);
        fillPaint.setColor(fillColor);
        fillPaint.setAlpha(50);
        gridPaint.setColor(gridColor);
        textPaint.setColor(textColor);
        invalidate();
    }
}