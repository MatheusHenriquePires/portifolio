/* =============================================
   SCRIPT.JS — Portfolio Motion + Carousel (fixed)
============================================= */

const { animate, inView, stagger } = window.Motion;

/* ─── Animated Grid Background (Canvas) ─── */
const canvas = document.getElementById('bg-canvas');
const ctx = canvas.getContext('2d');
let W, H, dots = [];

function resizeCanvas() { W = canvas.width = window.innerWidth; H = canvas.height = window.innerHeight; }
function initDots() {
  dots = [];
  const count = Math.floor((W * H) / 18000);
  for (let i = 0; i < count; i++) dots.push({
    x: Math.random() * W, y: Math.random() * H,
    r: Math.random() * 1.5 + 0.4,
    vx: (Math.random() - 0.5) * 0.25, vy: (Math.random() - 0.5) * 0.25,
    alpha: Math.random() * 0.5 + 0.1,
  });
}
function drawDots() {
  ctx.clearRect(0, 0, W, H);
  ctx.strokeStyle = 'rgba(0, 245, 255, 0.04)'; ctx.lineWidth = 1;
  for (let x = 0; x < W; x += 60) { ctx.beginPath(); ctx.moveTo(x, 0); ctx.lineTo(x, H); ctx.stroke(); }
  for (let y = 0; y < H; y += 60) { ctx.beginPath(); ctx.moveTo(0, y); ctx.lineTo(W, y); ctx.stroke(); }
  dots.forEach((d, i) => {
    d.x += d.vx; d.y += d.vy;
    if (d.x < 0 || d.x > W) d.vx *= -1;
    if (d.y < 0 || d.y > H) d.vy *= -1;
    ctx.beginPath(); ctx.arc(d.x, d.y, d.r, 0, Math.PI * 2);
    ctx.fillStyle = `rgba(0,245,255,${d.alpha})`; ctx.fill();
    for (let j = i + 1; j < dots.length; j++) {
      const d2 = dots[j], dist = Math.hypot(d.x - d2.x, d.y - d2.y);
      if (dist < 100) {
        ctx.beginPath(); ctx.moveTo(d.x, d.y); ctx.lineTo(d2.x, d2.y);
        ctx.strokeStyle = `rgba(0,245,255,${0.06 * (1 - dist / 100)})`; ctx.lineWidth = 0.5; ctx.stroke();
      }
    }
  });
  requestAnimationFrame(drawDots);
}
resizeCanvas(); initDots(); drawDots();
window.addEventListener('resize', () => { resizeCanvas(); initDots(); });

/* ─── Navbar ─── */
const navbar = document.getElementById('navbar');
window.addEventListener('scroll', () => navbar.classList.toggle('scrolled', window.scrollY > 60));

/* ─── Mobile Menu ─── */
const menuToggle = document.getElementById('menuToggle');
const mobileMenu = document.getElementById('mobileMenu');
menuToggle.addEventListener('click', () => {
  const isOpen = mobileMenu.classList.contains('open');
  if (!isOpen) { mobileMenu.classList.add('open'); animate(mobileMenu, { opacity: [0, 1], y: [-10, 0] }, { duration: 0.3 }); }
  else { animate(mobileMenu, { opacity: [1, 0], y: [0, -10] }, { duration: 0.2 }).then(() => mobileMenu.classList.remove('open')); }
});
document.querySelectorAll('.mobile-link').forEach(l => l.addEventListener('click', () => {
  animate(mobileMenu, { opacity: [1, 0], y: [0, -10] }, { duration: 0.2 }).then(() => mobileMenu.classList.remove('open'));
}));

/* ─── Hero Entrance ─── */
window.addEventListener('DOMContentLoaded', () => {
  animate('.hero-badge', { opacity: [0, 1], y: [20, 0] }, { duration: 0.6, delay: 0.2, easing: [0.22, 1, 0.36, 1] });
  animate('.greeting', { opacity: [0, 1], y: [20, 0] }, { duration: 0.5, delay: 0.35, easing: [0.22, 1, 0.36, 1] });
  animate('.name', { opacity: [0, 1], y: [30, 0] }, { duration: 0.6, delay: 0.45, easing: [0.22, 1, 0.36, 1] });
  animate('.role-wrapper', { opacity: [0, 1], y: [20, 0] }, { duration: 0.5, delay: 0.6, easing: [0.22, 1, 0.36, 1] });
  animate('.hero-bio', { opacity: [0, 1], y: [20, 0] }, { duration: 0.5, delay: 0.75, easing: [0.22, 1, 0.36, 1] });
  animate('.hero-cta', { opacity: [0, 1], y: [20, 0] }, { duration: 0.5, delay: 0.9, easing: [0.22, 1, 0.36, 1] });
  animate('.hero-stats', { opacity: [0, 1], y: [20, 0] }, { duration: 0.5, delay: 1.1, easing: [0.22, 1, 0.36, 1] });
  animate('.hero-visual', { opacity: [0, 1], x: [40, 0] }, { duration: 0.8, delay: 0.5, easing: [0.22, 1, 0.36, 1] });
  setTimeout(() => {
    animate('.tag-1', { opacity: [0, 1], x: [20, 0] }, { duration: 0.4 });
    animate('.tag-2', { opacity: [0, 1], x: [20, 0] }, { duration: 0.4, delay: 0.1 });
    animate('.tag-3', { opacity: [0, 1], x: [-20, 0] }, { duration: 0.4, delay: 0.2 });
  }, 1000);
});

/* ─── Typed Role ─── */
const roles = ['Dev Full Stack', 'Frontend Dev', 'Backend Dev', 'Problem Solver'];
let roleIdx = 0, charIdx = 0, isDeleting = false;
const typedEl = document.getElementById('typed-role');
function type() {
  const cur = roles[roleIdx];
  typedEl.textContent = isDeleting ? cur.substring(0, charIdx - 1) : cur.substring(0, charIdx + 1);
  isDeleting ? charIdx-- : charIdx++;
  let delay = isDeleting ? 60 : 100;
  if (!isDeleting && charIdx === cur.length) { delay = 2000; isDeleting = true; }
  else if (isDeleting && charIdx === 0) { isDeleting = false; roleIdx = (roleIdx + 1) % roles.length; delay = 400; }
  setTimeout(type, delay);
}
setTimeout(type, 1800);

/* ─── Stats Counter ─── */
function animateStat(el) {
  const target = parseInt(el.dataset.target);
  animate(p => { el.textContent = Math.round(p * target); }, { duration: 1.5, easing: [0.22, 1, 0.36, 1] });
}
inView('.hero-stats', () => document.querySelectorAll('.stat-number').forEach(animateStat), { amount: 0.5 });

/* ─── Section Reveals ─── */
document.querySelectorAll('.reveal').forEach((el, i) => {
  // Don't set opacity:0 on elements that contain critical content initially visible
  el.style.opacity = '0';
  el.style.transform = 'translateY(32px)';
  inView(el, () => {
    animate(el, { opacity: [0, 1], y: [32, 0] }, { duration: 0.6, delay: (i % 4) * 0.08, easing: [0.22, 1, 0.36, 1] });
  }, { amount: 0.1 });
});

/* ─── Skill Bars ─── */
inView('#skills', () => {
  document.querySelectorAll('.skill-fill').forEach((bar, i) => {
    setTimeout(() => { bar.style.width = bar.dataset.width + '%'; }, 200 + i * 80);
  });
  animate('.skill-category', { opacity: [0, 1], y: [30, 0] }, { delay: stagger(0.1), duration: 0.5, easing: [0.22, 1, 0.36, 1] });
}, { amount: 0.1 });

/* ─── Contact ─── */
inView('#contact', () => {
  animate('.contact-link', { opacity: [0, 1], x: [-20, 0] }, { delay: stagger(0.1), duration: 0.5, easing: [0.22, 1, 0.36, 1] });
}, { amount: 0.1 });

/* ─── Btn hover ─── */
document.querySelectorAll('.btn').forEach(btn => {
  btn.addEventListener('mouseenter', () => animate(btn, { scale: [1, 1.03] }, { duration: 0.2 }));
  btn.addEventListener('mouseleave', () => animate(btn, { scale: [1.03, 1] }, { duration: 0.2 }));
});

/* ─── Form submit ─── */
const form = document.getElementById('contactForm');
form.addEventListener('submit', e => {
  e.preventDefault();
  const btn = document.getElementById('form-submit');
  const orig = btn.innerHTML;
  btn.innerHTML = '<span>Mensagem enviada! ✅</span>';
  btn.style.background = '#27c93f';
  animate(btn, { scale: [1, 1.04, 1] }, { duration: 0.4 });
  setTimeout(() => { btn.innerHTML = orig; btn.style.background = ''; form.reset(); }, 2500);
});

/* ─── Active nav ─── */
const navLinks = document.querySelectorAll('.nav-links a');
new IntersectionObserver(entries => {
  entries.forEach(e => {
    if (e.isIntersecting) navLinks.forEach(a => {
      a.style.color = a.getAttribute('href') === `#${e.target.id}` ? 'var(--accent)' : '';
    });
  });
}, { threshold: 0.4 }).observe && document.querySelectorAll('section[id]').forEach(s =>
  new IntersectionObserver(entries => {
    entries.forEach(e => {
      if (e.isIntersecting) navLinks.forEach(a => {
        a.style.color = a.getAttribute('href') === `#${e.target.id}` ? 'var(--accent)' : '';
      });
    });
  }, { threshold: 0.4 }).observe(s)
);

/* ================================================
   CAROUSEL — simple display-based + Motion.dev
   Slides são display:none por padrão, apenas o
   ativo (current) é display:grid.
   Motion anima opacity e translateX.
================================================ */
const slides = Array.from(document.querySelectorAll('.carousel-slide'));
const dotBtns = Array.from(document.querySelectorAll('.carousel-dot'));
const prevBtn = document.getElementById('carouselPrev');
const nextBtn = document.getElementById('carouselNext');
let current = 0;
let isAnimating = false;

// Init: show only slide 0
slides.forEach((s, i) => {
  s.style.display = i === 0 ? 'grid' : 'none';
  s.style.opacity = i === 0 ? '1' : '0';
});

function goTo(next, dir = 1) {
  if (isAnimating || next === current) return;
  isAnimating = true;

  const outEl = slides[current];
  const inEl = slides[next];

  // Fade out current
  animate(outEl, { opacity: [1, 0], x: [0, dir * -40] }, { duration: 0.3, easing: 'ease-in' }).then(() => {
    outEl.style.display = 'none';

    // Prepare incoming slide
    inEl.style.display = 'grid';
    inEl.style.opacity = '0';

    // Animate in
    animate(inEl, { opacity: [0, 1], x: [dir * 40, 0] }, { duration: 0.4, easing: [0.22, 1, 0.36, 1] }).then(() => {
      isAnimating = false;
    });
  });

  // Update dots
  dotBtns[current].classList.remove('active');
  dotBtns[next].classList.add('active');
  animate(dotBtns[next], { scale: [1, 1.3, 1] }, { duration: 0.3 });

  current = next;
}

prevBtn.addEventListener('click', () => {
  animate(prevBtn, { scale: [1, 0.88, 1] }, { duration: 0.22 });
  goTo((current - 1 + slides.length) % slides.length, -1);
});

nextBtn.addEventListener('click', () => {
  animate(nextBtn, { scale: [1, 0.88, 1] }, { duration: 0.22 });
  goTo((current + 1) % slides.length, 1);
});

dotBtns.forEach(dot => {
  dot.addEventListener('click', () => {
    const next = parseInt(dot.dataset.dot);
    goTo(next, next > current ? 1 : -1);
  });
});

// Auto-play
let timer = setInterval(() => goTo((current + 1) % slides.length, 1), 5000);
const wrap = document.querySelector('.carousel-wrapper');
wrap.addEventListener('mouseenter', () => clearInterval(timer));
wrap.addEventListener('mouseleave', () => { timer = setInterval(() => goTo((current + 1) % slides.length, 1), 5000); });

// Touch swipe
let tx = 0;
wrap.addEventListener('touchstart', e => { tx = e.touches[0].clientX; }, { passive: true });
wrap.addEventListener('touchend', e => {
  const diff = tx - e.changedTouches[0].clientX;
  if (Math.abs(diff) > 50) goTo(diff > 0 ? (current + 1) % slides.length : (current - 1 + slides.length) % slides.length, diff > 0 ? 1 : -1);
});
